package org.theta.services;

import org.msh.quantb.model.forecast.Forecast;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.calc.ForecastingCalculation;


/**
 * The basic qtbengine features:
 * <ul>
 * <li> parse qtb parameters into Java class
 * <li> calculate daily forecasting for medicinal products
 * </ul>
 * Suit for quick starting and for 80% of all needs
 */
public class BasicEngine {

	public static Forecast calculateDailyForecast(Forecast forecast){
		// base calculation
		ForecastingCalculation calc = new ForecastingCalculation(forecast, ModelFactory.of(""));
		calc.clearResults();
		calc.calcCasesOnTreatment();
		calc.calcNewCases();
		calc.calcMedicinesRegimes();
		calc.adjustResults();
		calc.calcMedicines();
		calc.calcMedicinesResults();
		calc.shrinkRegimenResults();
		return forecast;
	}

}
