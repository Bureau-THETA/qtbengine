package org.msh.quantb.services.calc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.msh.quantb.model.forecast.Forecast;
import org.msh.quantb.model.forecast.ForecastingBatch;
import org.msh.quantb.model.forecast.ForecastingMedicine;
import org.msh.quantb.model.forecast.ForecastingOrder;
import org.msh.quantb.model.forecast.ForecastingRegimen;
import org.msh.quantb.model.forecast.ForecastingRegimenResult;
import org.msh.quantb.model.forecast.ForecastingResult;
import org.msh.quantb.model.forecast.MonthQuantity;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.ForecastingBatchUIAdapter;
import org.msh.quantb.services.io.ForecastingMedicineUIAdapter;
import org.msh.quantb.services.io.ForecastingOrderUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenResultUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenUIAdapter;
import org.msh.quantb.services.io.ForecastingResultUIAdapter;
import org.msh.quantb.services.io.MonthUIAdapter;

/**
 * Helper class to calculate needs of medicines kit at the first date of each month in a forecasting period 
 * @author alexk
 *
 */
public class KitCalculator {

	/**
	 * Disable the default constructor
	 */
	private KitCalculator() {}
	/**
	 * Only valid constructor
	 * @param forecastingRegimen
	 * @param results
	 * @return
	 */
	//forecast for regimen
	private Forecast forecast;
	//regimen for the kit
	private ForecastingRegimen fregimen = null;
	//the kit
	private ForecastingMedicine kit=null;
	//kit's batches sorted by expire date - ascending
	private List<ForecastingBatch> availableBatches = new ArrayList<ForecastingBatch>();


	public static KitCalculator of(Forecast forecast, ForecastingRegimen fr, ForecastingMedicine kit) {
		KitCalculator ret = new KitCalculator();
		ret.setForecast(forecast);
		ret.setFregimen(fr);
		ret.setKit(kit);
		return ret;
	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public ForecastingRegimen getFregimen() {
		return fregimen;
	}

	public void setFregimen(ForecastingRegimen fregimen) {
		this.fregimen = fregimen;
	}

	public ForecastingMedicine getKit() {
		return kit;
	}

	public void setKit(ForecastingMedicine kit) {
		this.kit = kit;
	}

	public List<ForecastingBatch> getAvailableBatches() {
		return availableBatches;
	}

	public void setAvailableBatches(List<ForecastingBatch> availableBatches) {
		this.availableBatches = availableBatches;
	}
	/**
	 * Consume from batches, determine missed and expired
	 * Assumed that forecasting result are exist for any kit
	 * Assumed that the forecasting regimen contains 
	 * Works very similar to BatchCalculator 
	 */
	public void consume() {
		List<ForecastingBatchUIAdapter> previous = null; //previous results contains medicines availability data 
		for(ForecastingResult frr : getKit().getResults()) {
			ForecastingResultUIAdapter fr = new ForecastingResultUIAdapter(frr);
			if(previous == null) {
				initBatches(fr);			//initialize from inventory
			}else {
				createBatches(fr, previous);			//initialize from the previous results
			}
			BigDecimal rest = consumeBatches(fr);	//consume today
			if (rest.compareTo(BigDecimal.ZERO) > 0){
				frr.setMissing(rest);						//rest are missing
			}
			previous = fr.getBatches();
		}
	}
	/**
	 * Consume existing batches only at the first date of a month and only for new cases - one case, one kit
	 * Determine missed and expired
	 * @param fr
	 * @return
	 */
	private BigDecimal consumeBatches(ForecastingResultUIAdapter fr) {
		//determine needs to consume, only in the first day of each month
		BigDecimal consume = BigDecimal.ZERO;	
		if(fr.getFromDay()==1) {
			consume = fetchNewCases(fr.getMonth());
		}
		//try to consume from batches sorted by expiration date
		for(ForecastingBatchUIAdapter fbui : fr.getBatches()) {	//a result contains all batches - inventory and orders
			if(fbui.getQuantityExpired()==0){
				if(kitJustExpired(fbui,fr)) {
					fbui.expire();
				}else {
					BigDecimal avail = fbui.getQuantityAvailable();
					if (avail.compareTo(BigDecimal.ZERO) > 0){ 			//we have something
						if (consume.compareTo(BigDecimal.ZERO) != 0){	//we still need consume
							if (consume.compareTo(avail)<=0){
								fbui.getForecastingBatchObj().setConsumptionInMonth(consume); //all consumed from the batch!
								consume = BigDecimal.ZERO;				
							}else{
								fbui.getForecastingBatchObj().setConsumptionInMonth(avail); //only avail consumed from this batch
								consume = consume.subtract(avail);	//maybe rest consumption will be from other batches, or not
							}
						}
					}
				}
			}
		}
		return consume; //if non-zero, then missed
	}

	/**
	 * Fetch new cases from the regime parameters for a month given
	 * @param month
	 * @return
	 */
	private BigDecimal fetchNewCases(MonthUIAdapter month) {
		BigDecimal ret = BigDecimal.ZERO;
		for(MonthQuantity mq : getFregimen().getNewCases()) {
			if(mq.getMonth().getYear()==month.getYear() && mq.getMonth().getMonth()==month.getMonth()) {
				ret=new BigDecimal(mq.getIQuantity());
				break;
			}
		}
		return ret;
	}

	/**
	 * A batch of kits may be account as expired if expiration date of the batch less then start treatment date plus regimen length
	 * @param fbui
	 * @param fr
	 * @return
	 */
	private boolean kitJustExpired(ForecastingBatchUIAdapter fbui, ForecastingResultUIAdapter fr) {
		ForecastingRegimenUIAdapter frui = new ForecastingRegimenUIAdapter(getFregimen());
		LocalDate endDate = DateUtils.convert(frui.getRegimen().getEndDate(fr.getFrom()));
				// LocalDate(frui.getRegimen().getEndDate( fr.getFrom()));
		LocalDate expired = DateUtils.convert(fbui.getExpired());
				//new LocalDate(fbui.getExpired());
		return expired.isBefore(endDate.minusDays(1)) && (fbui.getQuantityExpired()==0);
	}

	/**
	 * create batches for this day from batches from previous day
	 * @param fr
	 * @param previous
	 */
	private void createBatches(ForecastingResultUIAdapter fr, List<ForecastingBatchUIAdapter> previous) {
		fr.getForecastingResult().getBatches().clear();		//prepare empty list for batches
		for(ForecastingBatchUIAdapter fbu : previous){
			ForecastingBatchUIAdapter clone = fbu.makeClone(ModelFactory.of(""));
			if (clone.getQuantityAvailable().compareTo(BigDecimal.ZERO) > 0){	//reduce the availability by previous day consumption
				//it is assumed that avail quantity is enough, see method "consume"
				clone.setQuantityAvailable(clone.getQuantityAvailable().subtract(clone.getConsumptionInMonth()));
			}
			clone.setConsumptionInMonth(BigDecimal.ZERO);		//this day consumption is not defined yet
			determineAvailable(clone, fr);									//maybe it became available?
			clone.setQuantityExpired(0L);  //expiration will be defined in method "consume"
			fr.getForecastingResult().getBatches().add(clone.getForecastingBatchObj().getOriginal());
		}

	}
	/**
	 * kits in batch will be available just after date of availability
	 * @param batch
	 * @param result
	 */
	private void determineAvailable(ForecastingBatchUIAdapter batch,
			ForecastingResultUIAdapter result) {
		int year = result.getMonth().getYear();
		int month = result.getMonth().getMonth();
		int day = result.getFromDay();
		Calendar cal = batch.getAvailFrom();
		if ((cal.get(Calendar.YEAR) == year) && (cal.get(Calendar.MONTH) == month) && (cal.get(Calendar.DAY_OF_MONTH) == day)){
			batch.getForecastingBatchObj().setQuantityAvailable(new BigDecimal(batch.getQuantity()));
		}
	}

	/**
	 * Create batches from inventory and orders data and add them to forecasting result
	 * @param fr forecasting result to add batches
	 */
	private void initBatches(ForecastingResultUIAdapter fr) {
		fr.getBatches().clear();															//we will re-write batches in the result
		ForecastingMedicineUIAdapter kitUi = new ForecastingMedicineUIAdapter(getKit());
		for(ForecastingBatchUIAdapter fbu :  kitUi.getBatchesToExpire()){	//all batches in the kit sorted by expiration date
			ForecastingBatchUIAdapter clone = fbu.makeClone(ModelFactory.of(""));	//we will duplicate kit batch to regimen batches
			clone.setQuantityAvailable(new BigDecimal(clone.getQuantity()));
			determineAvailable(clone, fr);														//is this batch available for dispensing?
			clone.setQuantityExpired(0L);														//expiration we will check later - while consumption
			clone.setConsumptionInMonth(BigDecimal.ZERO);							//consumption in the previous day
			fr.getForecastingResult().getBatches().add(clone.getForecastingBatchObj().getOriginal());
		}
		for(ForecastingOrder ford : getKit().getOrders()) {
			ForecastingOrderUIAdapter fordui = new ForecastingOrderUIAdapter(ford);
			ForecastingBatchUIAdapter clone = fordui.getBatch().makeClone(ModelFactory.of(""));
			clone.setAvailFrom(fordui.getArrived());									//order will be available after arrive date
			clone.getForecastingBatchObj().setQuantityAvailable(BigDecimal.ZERO);	//only to ensure
			determineAvailable(clone, fr);
			fr.getForecastingResult().getBatches().add(clone.getForecastingBatchObj().getOriginal());
		}
	}
	/**
	 * Create results for kit and define needs at once!
	 * Needs are possible only at the first day of a month and only for new cases at this month
	 */
	public void defineNeeds() {
		this.getKit().getResults().clear();
		ForecastUIAdapter fui = new ForecastUIAdapter(getForecast());
		LocalDate beginDate = DateUtils.convert(fui.getReferenceDate());
				//new LocalDate(fui.getReferenceDate());
		LocalDate endDate = DateUtils.convert(fui.getEndDate());
				//new LocalDate(fui.getEndDate());
		for(ForecastingRegimenResult frr:getFregimen().getResults()) {
			ForecastingRegimenResultUIAdapter frrui = new ForecastingRegimenResultUIAdapter(frr);
			LocalDate resultDate = DateUtils.convert(frrui.getFromDate());
					//new LocalDate(frrui.getFromDate());
			if(resultDate.isAfter(beginDate) && !resultDate.isAfter(endDate)) {
				ForecastingResult fr = ModelFactory.of("").createForecastingResult(frr.getMonth());
				fr.setFromDay(frr.getFromDay());
				fr.setToDay(frr.getToDay());
				fr.setConsNew(BigDecimal.ZERO);
				fr.setConsOld(BigDecimal.ZERO);
				fr.setNewCases(frrui.getExpected());
				fr.setOldCases(frrui.getEnrolled());
				if(frr.getFromDay()==1) {
					fr.setConsNew(fetchNewCases(frrui.getMonth()));
				}
				this.getKit().getResults().add(fr);
			}
		}
	}

}
