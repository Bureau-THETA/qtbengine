package org.msh.quantb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.msh.quantb.model.forecast.Forecast;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.calc.DateUtils;
import org.msh.quantb.services.calc.ForecastingCalculation;
import org.msh.quantb.services.calc.MedicineConsumption;
import org.msh.quantb.services.calc.MedicineResume;
import org.msh.quantb.services.calc.PeriodResume;
import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.ForecastingMedicineUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenResultUIAdapter;
import org.msh.quantb.services.io.ForecastingRegimenUIAdapter;
import org.msh.quantb.services.io.ForecastingResultUIAdapter;
import org.msh.quantb.services.io.MedicineConsUIAdapter;

import jakarta.xml.bind.JAXBException;

public class Tests {
	
	@Test
	public void shouldLoadFromFileAndStream() {
		try {
			Forecast forecast =ModelFactory.of("").readForecasting("src/test/resources/test/", "Fictitia_2016_FASTER_V4.qtb");
			assertEquals(6,forecast.getLeadTime());
		} catch (FileNotFoundException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test enrolled and expected cases quantities
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void shouldCalcCasesQuantities() throws FileNotFoundException, JAXBException{
		ForecastUIAdapter fUi = new ForecastUIAdapter(ModelFactory.of("")
				.readForecasting("src/test/resources/test/", "TestCasesQuantity.qtb"));
		ForecastingCalculation fc = new ForecastingCalculation(fUi.getForecastObj(), ModelFactory.of(""));
		// imitate execute method
		fc.clearResults();
		fc.calcCasesOnTreatment();
		fc.calcNewCases();
		fc.calcMedicinesRegimes();
		fc.calcMedicines();
		fc.calcMedicinesResults();
		List<MedicineResume> res = fc.getResume();

		List<ForecastingRegimenUIAdapter> list = fc.getForecastUI().getRegimes();
		ForecastingRegimenUIAdapter ui = list.get(0);
		List<ForecastingRegimenResultUIAdapter> fRes = ui.getResults();
		//check result quantity
		assertEquals(679, fRes.size());
		//First random point
		ForecastingRegimenResultUIAdapter rC = fRes.get(179);
		System.out.println("First random " + DateUtils.formatDate(rC.getFromDate().getTime(), "dd.MM.yyyy"));
		assertEquals(3,rC.getIntensive().getOldCases().intValue());
		assertEquals(0,rC.getContinious().getOldCases().intValue());
		assertEquals(11, rC.getAdditionalPhases().get(0).getOldCases().intValue());
		assertEquals(2, rC.getAdditionalPhases().get(1).getOldCases().intValue());
		//Second random point
		rC = fRes.get(357);
		System.out.println("Second random " + DateUtils.formatDate(rC.getFromDate().getTime(), "dd.MM.yyyy"));
		assertEquals(3,rC.getIntensive().getNewCases().intValue());
		assertEquals(7,rC.getContinious().getNewCases().intValue());
		assertEquals(4, rC.getAdditionalPhases().get(0).getNewCases().intValue());
		assertEquals(2, rC.getAdditionalPhases().get(1).getNewCases().intValue());
		// third random point
		rC = fRes.get(246); 
		System.out.println("Third random " + DateUtils.formatDate(rC.getFromDate().getTime(), "dd.MM.yyyy"));
		assertEquals(0,rC.getIntensive().getOldCases().intValue());
		assertEquals(0,rC.getContinious().getOldCases().intValue());
		assertEquals(3, rC.getAdditionalPhases().get(0).getOldCases().intValue());
		assertEquals(7, rC.getAdditionalPhases().get(1).getOldCases().intValue());
		assertEquals(11,rC.getIntensive().getNewCases().intValue());
		assertEquals(0,rC.getContinious().getNewCases().intValue());
		assertEquals(0, rC.getAdditionalPhases().get(0).getNewCases().intValue());
		assertEquals(0, rC.getAdditionalPhases().get(1).getNewCases().intValue());

		//check regimen - medicines
		int i = 0;
		for(ForecastingMedicineUIAdapter mUi : fUi.getMedicines()){
			MedicineConsUIAdapter mcUi = rC.getMedConsunption(mUi.getMedicine());
			if (mcUi != null){
				System.out.println(mcUi);
				if (mcUi.getMedicine().getAbbrevName().contains("Am(500)")){
					assertEquals(22, mcUi.getConsIntensiveNew().intValue());
					assertEquals(22, mcUi.getAllConsumption().intValue());
					i++;
				}
				if(mcUi.getMedicine().getAbbrevName().contains("Am(500/2)")){
					assertEquals(0, mcUi.getAllConsumption().intValue());
					i++;
				}
				if(mcUi.getMedicine().getAbbrevName().contains("Cm(1000)")){
					assertEquals(3, mcUi.getConsOtherOld().get(0).intValue());
					assertEquals(3, mcUi.getAllConsumption().intValue());
					i++;
				}
				if(mcUi.getMedicine().getAbbrevName().contains("Imi/Cls(500/500)")){
					assertEquals(7, mcUi.getConsOtherOld().get(1).intValue());
					assertEquals(7, mcUi.getAllConsumption().intValue());
					i++;
				}
			}
			//check medicines only - The Result! Third random point. 69, because all results are from th Reference Date
			ForecastingResultUIAdapter fRu = mUi.getResults().get(69);
			if (mUi.getMedicine().getAbbrevName().contains("Am(500)")){
				assertEquals(22, fRu.getConsNew().intValue());
				assertEquals(11, fRu.getNewCases().intValue());
				assertEquals(0, fRu.getConsOld().intValue());
				assertEquals(0, fRu.getOldCases().intValue());
				i++;
			}
			if (mUi.getMedicine().getAbbrevName().contains("Am(500/2)")){
				int res1 = fRu.getConsOld().intValue() + fRu.getConsNew().intValue()
						+ fRu.getNewCases().intValue() + fRu.getOldCases().intValue();
				assertEquals(0, res1);
				i++;
			}
			if (mUi.getMedicine().getAbbrevName().contains("Cm(1000)")){
				assertEquals(0, fRu.getConsNew().intValue());
				assertEquals(0, fRu.getNewCases().intValue());
				assertEquals(3, fRu.getConsOld().intValue());
				assertEquals(3, fRu.getOldCases().intValue());
				i++;
			}

			if (mUi.getMedicine().getAbbrevName().contains("Imi/Cls(500/500)")){
				assertEquals(0, fRu.getConsNew().intValue());
				assertEquals(0, fRu.getNewCases().intValue());
				assertEquals(7, fRu.getConsOld().intValue());
				assertEquals(7, fRu.getOldCases().intValue());
				i++;
			}
		}
		assertEquals(8, i);	// have all tests been passed?

		//check medicine consumption and Resume (see page Medicines in table casesNoTest.xlsx)
		Calendar endDate = DateUtils.getCleanCalendar(2014, 5, 1);
		i = 0;
		for(ForecastingMedicineUIAdapter mUi : fUi.getMedicines()){
			BigDecimal cases = BigDecimal.ZERO;
			int dispensed = 0; 
			for (ForecastingResultUIAdapter frUi : mUi.getResults()){
				if (frUi.getFrom().before(endDate)){
					//System.out.println(DateUtils.formatDate(frUi.getFrom().getTime(), "dd.MM.yyyy"));
					cases = cases.add(frUi.getOldCases());
					cases = cases.add(frUi.getNewCases());
					dispensed = dispensed + frUi.getMedicineConsInt();
				}else{
					break;
				}
			}

			if (mUi.getMedicine().getAbbrevName().contains("Am(500)")){
				assertTrue(cases.compareTo(new BigDecimal(120)) == 0);
				assertEquals(240, dispensed);
				assertEquals(1918,res.get(0).getLeadPeriod().getDispensed().intValue());
				i++;
			}
			if(mUi.getMedicine().getAbbrevName().contains("Am(500/2)")){
				assertTrue(cases.compareTo(new BigDecimal(0)) == 0);
				assertEquals(0, dispensed);
				assertEquals(42,res.get(1).getLeadPeriod().getDispensed().intValue());
				i++;
			}
			if (mUi.getMedicine().getAbbrevName().contains("Cm(1000)")){
				assertTrue(cases.compareTo(new BigDecimal(145)) == 0);
				assertEquals(145, dispensed);
				assertEquals(446,res.get(2).getLeadPeriod().getDispensed().intValue());
				i++;
			}
			if (mUi.getMedicine().getAbbrevName().contains("Imi/Cls(500/500)")){
				assertTrue(cases.compareTo(new BigDecimal(43)) == 0);
				assertEquals(43, dispensed);
				assertEquals(420,res.get(3).getLeadPeriod().getDispensed().intValue());
				i++;
			}
		}
		assertEquals(4, i);
		//test monthly cases every fRuI is first date of a month - Cases report - Treatment regimes
		List<ForecastingRegimenResultUIAdapter> fRuI = fUi.getRegimes().get(0).getMonthsResults(fUi.getFirstFCDate(),ModelFactory.of(""));
		i=0;
		int firstDate =17;
		for(ForecastingRegimenResultUIAdapter frr : fRuI){
			assertEquals(firstDate, frr.getFromDay().intValue());
			firstDate = 1;
			if (frr.getMonth().getMonth().intValue() == 4 && frr.getMonth().getYear().intValue()==2014){
				assertEquals(21, frr.getEnrolled().intValue());
				assertEquals(5, frr.getExpected().intValue());
				i++;
			}
			if (frr.getMonth().getMonth().intValue() == 7 && frr.getMonth().getYear().intValue()==2014){
				assertEquals(3, frr.getEnrolled().intValue());
				assertEquals(18, frr.getExpected().intValue());
				i++;
			}
			if (frr.getMonth().getMonth().intValue() == 1 && frr.getMonth().getYear().intValue()==2015){
				assertEquals(0, frr.getEnrolled().intValue());
				assertEquals(3, frr.getExpected().intValue());
				i++;
			}
			if (frr.getMonth().getMonth().intValue() == 2 && frr.getMonth().getYear().intValue()==2015){
				assertEquals(0, frr.getEnrolled().intValue());
				assertEquals(0, frr.getExpected().intValue());
				i++;
			}
		}
		assertEquals(4, i);
		//test medicine consumption Cases Report - Medicine
		List<MedicineConsumption> mCons = fc.getMedicineConsumption();
		//first point at RD 17.05.2014
		int newCases = mCons.get(0).getCons().get(0).getNewCases().intValue();
		int oldCases = mCons.get(0).getCons().get(0).getOldCases().intValue();
		assertEquals(3, oldCases);
		assertEquals(5, newCases);

		//second point at 01.07.2014 and 4-th phase
		newCases = mCons.get(3).getCons().get(2).getNewCases().intValue();
		oldCases = mCons.get(3).getCons().get(2).getOldCases().intValue();
		assertEquals(7, oldCases);
		assertEquals(0, newCases);

		//third point at 17.08.2014 and 2-nd phase
		newCases = mCons.get(1).getCons().get(3).getNewCases().intValue();
		oldCases = mCons.get(1).getCons().get(3).getOldCases().intValue();
		assertEquals(0, oldCases);
		assertEquals(5, newCases);

		//test detailed report - 4 random points
		ForecastingMedicineUIAdapter med = fc.getForecastUI().getMedicines().get(0);
		List<PeriodResume> periods = fc.calcMedicineResume(med.getMedicine());
		assertEquals(90, periods.get(0).getConsumedOld().intValue());
		assertEquals(150, periods.get(0).getConsumedNew().intValue());
		assertEquals(0, periods.get(4).getConsumedOld().intValue());
		assertEquals(840, periods.get(4).getConsumedNew().intValue());

		med = fc.getForecastUI().getMedicines().get(2);
		periods = fc.calcMedicineResume(med.getMedicine());
		assertEquals(223, periods.get(1).getConsumedOld().intValue());
		assertEquals(0, periods.get(1).getConsumedNew().intValue());
		assertEquals(0, periods.get(6).getConsumedOld().intValue());
		assertEquals(212, periods.get(6).getConsumedNew().intValue());
	}
	
}
