package org.stoptb.quantbcalc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.xml.bind.JAXBException;

//import javax.xml.bind.JAXBException;
//import jakarta.xml.bind.JAXBException;
//import javax.xml.bind.JAXBException;

import org.msh.quantb.model.forecast.Forecast;
import org.msh.quantb.model.forecast.ForecastingRegimen;
import org.msh.quantb.model.forecast.ForecastingRegimenResult;
import org.msh.quantb.model.gen.DeliveryScheduleEnum;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.calc.ConsumptionMonth;
import org.msh.quantb.services.calc.DeliveryOrdersControl;
import org.msh.quantb.services.calc.ForecastingCalculation;
import org.msh.quantb.services.calc.ForecastingError;
import org.msh.quantb.services.calc.KitCalculator;
import org.msh.quantb.services.calc.LogisticCalculatorI;
import org.msh.quantb.services.calc.LogisticCalculatorsFactory;
import org.msh.quantb.services.calc.MedicineConsumption;
import org.msh.quantb.services.calc.OrderCalculator;
import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.ForecastingMedicineUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenResultUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenUIAdapter;
import org.msh.quantb.services.io.KitDefinitionUIAdapter;
import org.msh.quantb.services.io.MedicineUIAdapter;
import jakarta.xml.bind.JAXBException;

/**
 * This class implements main method to check performance load, validate,
 * calculate methods are API Those API are very easy - first call load, then
 * validate or calculate or in any sequence
 * 
 * @author Alex Kurasoff
 *
 */
public class Calculator {
	// Singleton
	private static volatile Calculator instance = null;
	private static Object mutex = new Object();
	//private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

	private Calculator() {
		super();
	}

	/**
	 * Thread safe instance of Processor
	 * 
	 * @return
	 */
	public static synchronized Calculator instanceOf() {
		Calculator result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new Calculator();
			}
		}
		return result;
	}

	/**
	 * Print diagnostic data
	 * 
	 * @param prevTime  when printDiag has been called before.
	 * @param logStream
	 */
	private static Date printDiag(Date prevTime, PrintWriter logStream) {
		int mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		log("##### Heap utilization statistics [MB] #####", logStream);

		// Print used memory
		log("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb, logStream);

		// Print free memory
		log("Free Memory:" + runtime.freeMemory() / mb, logStream);

		// Print total available memory
		log("Total Memory:" + runtime.totalMemory() / mb, logStream);

		// Print Maximum available memory
		log("Max Memory:" + runtime.maxMemory() / mb, logStream);

		// Print time elapsed
		Date ret = new Date();
		log("Elapsed " + ((ret.getTime() - prevTime.getTime()) / 1000) + " sec", logStream);
		return ret;
	}

	/**
	 * Main method to run from command line. Mainly for performance assessment
	 * purpose
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			File dir = new File(args[0]);
			if (dir.isDirectory()) {
				File[] directoryListing = dir.listFiles();
				if (directoryListing != null && directoryListing.length>0) {
					System.out.println("Press Enter to continue...");
					System.in.read();
					Date prevTime = new Date();
					Date start = new Date();
					int count = 0;
					File log = new File("log.txt");
					PrintWriter logStream = new PrintWriter(log);
					for (File child : directoryListing) {
						if (child.getName().toUpperCase().endsWith(".QTB")) {
							try {
								FileInputStream stream = new FileInputStream(child);
								log("Processing " + child.getName(), logStream);
								ForecastUIAdapter forecast = Calculator.instanceOf().load(stream, child.getName());
								ForecastResult result=Calculator.instanceOf().calculate(forecast);
								//logForecast(result,logStream);
								log("processed", logStream);
								prevTime = printDiag(prevTime, logStream);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (JAXBException e) {
								e.printStackTrace();
							}
						}
						count++;
					}
					Date endTime = new Date();
					log("Total elapsed " + ((endTime.getTime() - start.getTime()) / 1000) + " sec", logStream);
					log("Processed " + count + " forecasts", logStream);
					log("Average calculation time is " + ((endTime.getTime() - start.getTime()) / 1000) / count
							+ " sec", logStream);
					logStream.flush();
					logStream.close();
				} else {
					System.err.println(
							"Usage: quantbcalc qtb_directory" + dir.getAbsolutePath());
				}
			} else {
				System.err.println("Usage: quantbcalc qtb_directory" + dir.getAbsolutePath());
			}
		} else {
			System.err.println("Usage: quantbcalc qtb_directory");
		}

	}
	/**
	 * Log a random forecast record
	 * @param forecast
	 * @param logStream
	 */
	private static void logRandomForecast(ForecastUIAdapter forecast, PrintWriter logStream) {
		BigDecimal all=forecast.getMedicines().get(0).getResults().get(0).getAllAvailable();
		BigDecimal consOld=forecast.getMedicines().get(0).getResults().get(0).getConsOld();
		BigDecimal consNew=forecast.getMedicines().get(0).getResults().get(0).getConsNew();
		BigDecimal disp=forecast.getMedicines().get(0).getResults().get(0).getDispensing();
		BigDecimal miss=forecast.getMedicines().get(0).getResults().get(0).getMissing();
		Long expired=forecast.getMedicines().get(0).getResults().get(0).getExpired();
		int year=forecast.getMedicines().get(0).getResults().get(0).getMonth().getYear();
		int month=forecast.getMedicines().get(0).getResults().get(0).getMonth().getMonth();
		
	}

	/**
	 * Log it to console and file
	 * 
	 * @param message
	 * @param logStream
	 */
	private static void log(String message, PrintWriter logStream) {
		System.out.println(message);
		logStream.println(message);

	}

	/**
	 * Load forecast from qtb xml stream
	 * 
	 * @param qtbStream stream
	 * @param name      name of the stream that should be assigned
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public ForecastUIAdapter load(InputStream qtbStream, String name) throws JAXBException, IOException {
		Forecast fc = ModelFactory.of("").readForecastingFromStream(qtbStream, name);
		qtbStream.close();
		return new ForecastUIAdapter(fc);
	}

	/**
	 * Validate forecast
	 * 
	 * @param forecast
	 * @return list of errors or empty list if no errors
	 */
	public List<String> validate(ForecastUIAdapter forecast) {
		List<String> err = new ArrayList<String>();
		String s = forecast.verifyParameters();
		if (s.length() > 0) {
			err.add(s);
		}
		ForecastingCalculation calc = new ForecastingCalculation(forecast.getForecastObj(), ModelFactory.of(""));
		calc.checkRegimensAndMedicines(); // in some cases when user change regimen set
		calc.checkPercents(); // is percents valid ?
		calc.checkTotalCases(); // does we have at least one case - enrolled or expected
		calc.checkBatches(); // check batches dates
		List<ForecastingError> fErrs = calc.getError();
		if (fErrs.size() > 0) {
			for (ForecastingError e : fErrs) {
				err.add(e.getMessage());
			}
		}
		return err;
	}

	/**
	 * Calculates a forecast Presume that forecast is valid
	 * 
	 * @param forecast
	 * @return
	 */
	public ForecastResult calculate(ForecastUIAdapter forecast) {
		// disable the regular quantification for the regimes with kits
		Map<String, Boolean> storeOnTreatmentFlags = new HashMap<String, Boolean>();
		Map<String, Boolean> storeNewCasesFlags = new HashMap<String, Boolean>();
		for (ForecastingRegimenUIAdapter fru : forecast.getRegimes()) {
			if (!fru.getKitUI().isNotKit()) {
				storeOnTreatmentFlags.put(fru.getRegimen().getBusinessKey(), fru.isExcludeCasesOnTreatment());
				fru.setExcludeCasesOnTreatment(true);
				storeNewCasesFlags.put(fru.getRegimen().getBusinessKey(), fru.isExcludeNewCases());
				fru.setExcludeNewCases(true);
			}
		}
		//
		// base calculation
		ForecastingCalculation calc = new ForecastingCalculation(forecast.getForecastObj(), ModelFactory.of(""));
		calc.getForecastUI().setCollector(forecast.getCollector());
		calc.clearResults();
		calc.calcCasesOnTreatment();
		calc.calcNewCases();
		calc.calcMedicinesRegimes();
		calc.adjustResults();
		calc.calcMedicines();
		calc.calcMedicinesResults();
		// additional calculation for kits
		for (ForecastingRegimenUIAdapter fru : calc.getForecastUI().getRegimes()) {
			if (!fru.getKitUI().isNotKit()) {
				fru.setExcludeCasesOnTreatment(storeOnTreatmentFlags.get(fru.getRegimen().getBusinessKey()));
				fru.setExcludeNewCases(storeNewCasesFlags.get(fru.getRegimen().getBusinessKey()));
				calculateKits(calc, fru);
			}
		}
		// advanced calculation
		calc.getResume();
		List<MedicineConsumption> mCons = calc.getMedicineConsumption();
		// missed and expirations are known, so
		calc.prepareAttentions();

		// For each month for each medicine calculate monthly deliveries and estimate
		// how many stock on hand will be
		// if deliveries will occurred
		OrderCalculator oCalc = new OrderCalculator(calc);
		oCalc.execute();
		ForecastResult result = new ForecastResult();
		// save an original schedule
		DeliveryScheduleEnum savedSchedule = calc.getForecastUI().getDeliverySchedule();
		// Calculate for all possible schedules
		for (DeliveryScheduleEnum schedule : DeliveryScheduleEnum.values()) {
			calc.getForecastUI().setDeliverySchedule(schedule);
			// monthly deliveries are known, so build real delivery schedule
			// (List<DeliveryItemUI> for each medicine).
			// Actually we do not need them for Collector, HOWEVER!!! These items will be
			// used on the next step
			oCalc.getControl().buildAllExact();
			// for each medicine create a copy of ConsumptionMonth list with deliveries in
			// accordance with the schedule
			// it is necessary for graphs only, because DeliveryItemUIs we are have already
			for (MedicineConsumption mCon : mCons) {
				List<ConsumptionMonth> cMonth = buildConsumptionSet(mCon, forecast, oCalc.getControl(),false);
				List<ConsumptionMonth> cMonthPeriod = buildConsumptionSet(mCon, forecast, oCalc.getControl(),true);
				ScheduledConsumption sCons = ScheduledConsumption.of(mCon.getMed(), schedule);
				sCons.getConsumptions().addAll(cMonth);						//traditional QuanTB - from the first month of a period
				sCons.getConsumptionsPeriod().addAll(cMonthPeriod);	//Collector way - fixed length period
				result.getMonthlySummary().add(sCons);
			}
		}
		// restore an original schedule
		calc.getForecastUI().setDeliverySchedule(savedSchedule);
		// clear memory
		// fill results to the some intermediate class. Moved here 2022-05-16 from the cycle above
		result.setRegimes(createRegimens(forecast.getForecastObj().getRegimes()));
		ModelFactory.of("").cleanUpForecast(forecast.getForecastObj());
		calc.clearResults();
		return result;
	}

	/**
	 * Calculate needs for medicines kits
	 * 
	 * @param calc
	 * @param fru
	 * @param original
	 */
	private void calculateKits(ForecastingCalculation calc, ForecastingRegimenUIAdapter fru) {
		// calculate cases quantity for kits served regimes
		Calendar begin = calc.getForecastUI().getVeryFirstDate();
		Calendar end = calc.getForecastUI().getLastDate();
		fru.getFcRegimenObj().getResults().clear();
		calc.calcCasesOnTreatmentRegimen(begin, end, fru);
		calc.calcNewCasesRegimen(begin, end, fru);
		// find the kit in the forecasting medicines list
		ForecastingMedicineUIAdapter kitUi = findMedicineByKit(calc.getForecastUI(), fru.getKitUI());
		if (kitUi != null) {
			KitCalculator kitCalc = KitCalculator.of(calc.getForecastUI().getForecastObj(),
					fru.getFcRegimenObj().getOriginal(), kitUi.getFcMedicineObj());
			kitCalc.defineNeeds();
			kitCalc.consume();
		} else {
			//logger.error("Medicine (kit) not found. Name is " + fru.getKitUI().getKitName());
		}
	}

	/**
	 * Find medicine that represents the kit
	 * 
	 * @param forecast
	 * @param kitDefinition
	 * @return null, if not found
	 */
	private ForecastingMedicineUIAdapter findMedicineByKit(ForecastUIAdapter fui, KitDefinitionUIAdapter kitUi) {
		ForecastingMedicineUIAdapter ret = null;
		MedicineUIAdapter kitMui = MedicineUIAdapter.of(kitUi.getKitDefinition());
		for (ForecastingMedicineUIAdapter fmUi : fui.getMedicines()) {
			if (fmUi.getMedicine().createQuanTbKey().equals(kitMui.createQuanTbKey())) {
				ret = fmUi;
				break;
			}
		}
		return ret;
	}

	/**
	 * Build the consumption set based on the current delivery schedule all
	 * calculation will be on the clone of real consumption list
	 * @param collectorMode - true period grounded deliveries, false - traditional QuanTB
	 * 
	 * @return
	 */
	private List<ConsumptionMonth> buildConsumptionSet(MedicineConsumption mConsumption, ForecastUIAdapter forecast,
			DeliveryOrdersControl control, boolean collectorMode) {
		List<ConsumptionMonth> consumptionSet = new ArrayList<ConsumptionMonth>();
		for (ConsumptionMonth cM : mConsumption.getCons()) {
			consumptionSet.add(cM.getClone());
		}
		// recalculate deliveries in accordance with the schedule, currently is
		// Provisional!
		// clean up all deliveries
		for (ConsumptionMonth cM : consumptionSet) {
			cM.setDelivery(BigDecimal.ZERO);
		}
		// calculate incoming without deliveries
		LogisticCalculatorI lCalc = LogisticCalculatorsFactory.getLolgisticCalculator(forecast);
		// lCalc.recalcPStocks(consumptionSet, mConsumption.getMed());
		// //store incoming without deliveries
		// storePStock(consumptionSet);
		// add recalculated deliveries from the control
		for (ConsumptionMonth cM : consumptionSet) {
			BigDecimal delivery = control.fetchDeliveryPeriod(mConsumption.getMed(), cM.getMonth(), collectorMode);
			if (delivery.compareTo(BigDecimal.ZERO) > 0) {
				cM.setDelivery(delivery);
			}
			// calculate incoming with deliveries just added
			lCalc.recalcPStocks(consumptionSet, mConsumption.getMed());
		}
		return consumptionSet;
	}
	/*
		*//**
			 * Store projected stock values without deliveries
			 * 
			 * @param consumptionSet
			 *//*
				private void storePStock(List<ConsumptionMonth> consumptionSet) {
				for (ConsumptionMonth cm : consumptionSet) {
					cm.setP0Stock(cm.getpStock());
				}
				
				}
				*/

	/**
	 * Create results for each regimen
	 * 
	 * @param regimes
	 * @return
	 */
	public List<ForecastingRegimen> createRegimens(List<ForecastingRegimen> regimes) {
		List<ForecastingRegimen> ret = new ArrayList<>();
		for (ForecastingRegimen reg : regimes) {
			ForecastingRegimen newReg = new ForecastingRegimen();
			newReg.setRegimen(reg.getRegimen());
			newReg.getCasesOnTreatment().addAll(reg.getCasesOnTreatment());
			newReg.getNewCases().addAll(reg.getNewCases());
			newReg.setPercentCasesOnTreatment(reg.getPercentCasesOnTreatment());
			newReg.setPercentNewCases(reg.getPercentNewCases());
			if (reg.getResults() != null && reg.getResults().size() > 0) {
				int year = reg.getResults().get(0).getMonth().getYear();
				int month = reg.getResults().get(0).getMonth().getMonth();
				newReg.getResults().add(cloneRegimenResult(reg.getResults().get(0)));
				for (ForecastingRegimenResult res : reg.getResults()) {
					if (res.getMonth().getYear() != year || res.getMonth().getMonth() != month) {
						newReg.getResults().add(cloneRegimenResult(res));
						year = res.getMonth().getYear();
						month = res.getMonth().getMonth();
					}
				}
			}
			ret.add(newReg);
		}
		return ret;
	}

	/**
	 * Clone result of regimen related forecast
	 * 
	 * @param result
	 * @return
	 */
	private ForecastingRegimenResult cloneRegimenResult(ForecastingRegimenResult result) {
		ForecastingRegimenResultUIAdapter uiOriginal = new ForecastingRegimenResultUIAdapter(result);
		return uiOriginal.makeClone(ModelFactory.of("")).getResultObj();
	}

	/**
	 * Store forecast to byte array
	 * 
	 * @param forecastObj
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public byte[] storeToBytes(Forecast forecastObj) throws JAXBException, IOException {
		return ModelFactory.of("").storeForecastToBytes(forecastObj);
	}

}
