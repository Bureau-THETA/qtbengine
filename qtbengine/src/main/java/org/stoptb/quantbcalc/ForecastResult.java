package org.stoptb.quantbcalc;

import java.util.ArrayList;
import java.util.List;

import org.msh.quantb.model.forecast.ForecastingRegimen;


/**
 * This class transfers forecast result. This is not DTO!!!!
 * Forecast result consist of two parts:
 * <ul>
 * <li>deliveries - list of deliveries that will be needed to obey min/max stock constrains
 * <li>summaries - stock forecast summaries 
 * </ul>
 * @author Alex Kurasoff
 *
 */
public class ForecastResult {
	private boolean valid;
	private String error;
	private List<ScheduledConsumption> monthlySummary = new ArrayList<ScheduledConsumption>();
	private List<ForecastingRegimen> regimes = new ArrayList<ForecastingRegimen>();
	


	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public List<ScheduledConsumption> getMonthlySummary() {
		return monthlySummary;
	}
	public void setMonthlySummary(List<ScheduledConsumption> monthlySummary) {
		this.monthlySummary = monthlySummary;
	}
	public List<ForecastingRegimen> getRegimes() {
		return regimes;
	}
	public void setRegimes(List<ForecastingRegimen> regimes) {
		this.regimes.clear();
		this.regimes.addAll(regimes);
	}
	
}
