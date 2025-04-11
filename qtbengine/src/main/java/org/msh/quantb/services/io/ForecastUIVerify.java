package org.msh.quantb.services.io;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.msh.quantb.model.forecast.ForecastingOrder;
import org.msh.quantb.services.calc.DateUtils;
import org.msh.quantb.services.mvp.Messages;

/**
 * This class responsible to verify data input "on fly" in ForecastingWithardDlg and 
 * ForecastingDocumentPanel forms. <b> Warning messages and all validation</b><br>
 * Some methods of this class are static. These methods are for unify validation rules
 * @author alexey
 *
 */
public class ForecastUIVerify {

	private ForecastUIAdapter forecast;
	/**
	 * only valid constructor
	 * @param _forecast
	 */
	public ForecastUIVerify(ForecastUIAdapter _forecast){
		this.forecast = _forecast;
	}



	public ForecastUIAdapter getForecast() {
		return forecast;
	}



	public void setForecast(ForecastUIAdapter forecast) {
		this.forecast = forecast;
	}

	/**
	 * Does forecasting period contains from and to dates and these dates
	 * 
	 * @return string with error 
	 */
	public String checkPeriodEntered() {
		if(forecast.getIniDate() == null || forecast.getEndDate() == null){
			return Messages.getString("Forecasting.error.blankintreval");
		}
		return "";
	}

	/**
	 * Check max stock field
	 * @return error string, if validation not passed or empty one if passed
	 */
	public String checkMaxStock() {
		if (forecast.getMaxStock()<forecast.getMinStock() && forecast.getMaxStock()>0){
			return Messages.getString("Forecasting.error.minmaxstock");
		}
		if (forecast.getMaxStock()==forecast.getMinStock() && forecast.getMaxStock()>0){
			return Messages.getString("Forecasting.error.minmaxeq");
		}

		return "";
	}

	/**
	 * Check min stock
	 * @return error string, if validation not passed or empty one if passed
	 */
	public String checkMinStock() {
		if (forecast.getMaxStock()<forecast.getMinStock() && forecast.getMinStock()>0 && forecast.getMaxStock()>0){
			return Messages.getString("Forecasting.error.minmaxstock");
		}
		return "";
	}
	/**
	 * Check lead time
	 * @return error string, if validation not passed or empty one if passed
	 */
	public String checkLeadTime() {
		if (forecast.getLeadTime() == 0){
			return Messages.getString("Forecast.warning.leadtime");
		}
		return "";
	}


	/**
	 * This is common rule to check reference date
	 * @param rd reference date
	 * @param fcEnd end of forecasting
	 * @param lead the lead time in months
	 * @return message about first error found or enpty string if all OK
	 */
	public static String ruleReferenceDate(Calendar rd, Calendar fcEnd, int lead){
		String ret = "";
		if(rd != null){
			LocalDate lRd = DateUtils.convert(rd);
					//new LocalDate(rd);
			LocalDate lIni = lRd.plusDays(1).plusMonths(lead);
			if(fcEnd != null){
				LocalDate lFcEnd = DateUtils.convert(fcEnd);
						//new LocalDate(fcEnd);
				if(lIni.isAfter(lFcEnd) || lIni.isEqual(lFcEnd)){		//RULE 1 - reference date should fit period entered
					return Messages.getString("Error.forecasting.refDateNotFit");
				}
			}
		}
		return ret;
	}



	/**
	 * Check init date
	 * @param evt PropertyChange Event for check on fly or null for regular check
	 * @return error string, if validation not passed or empty one if passed
	 */
	public String checkIniDate(PropertyChangeEvent evt) {
		if (evt!=null){
			return ForecastUIVerify.checkInventoryDate(evt.getNewValue(), getForecast());
		}else{
			return "";// for divide ! ForecastUIVerify.checkInventoryDate(getForecast().getReferenceDate(), getForecast());
		}
	}

	/**
	 * Check end forecasting period date
	 * @param evt PropertyChange Event for check on fly or null for regular check
	 * @return error string, if validation not passed or empty one if passed
	 */
	public String checkEndDate(PropertyChangeEvent evt) {
		if(evt!=null){
			return ForecastUIVerify.checkEndDate(evt.getNewValue(), getForecast());
		}else{
			return ForecastUIVerify.checkEndDate(getForecast().getEndDate(), getForecast());
		}
	}
	/**
	 * Verify all UI fields - parameters
	 * @return error message or empty string
	 */
	public String verifyUI() {
		String s = checkPeriodEntered();
		if (s.length()>0) return s;
		s = checkIniDate(null);
		if (s.length()>0) return s;
		s = checkEndDate(null);
		if (s.length()>0) return s;
		s = checkMinStock();
		if (s.length()>0) return s;
		s = checkMaxStock(); 
		if (s.length()>0) return s;
		s = checkAuthor();
		if (s.length()>0) return s;
		s = checkOrders();
		if (s.length()>0) return s;
		return "";
	}



	/**
	 * Additional check for order date - expiration date in orders
	 * It is possible to export wrong dates
	 * @return
	 */
	private String checkOrders() {
		String ret = "";
		List<ForecastingMedicineUIAdapter> meds = getForecast().getMedicines();
		for(ForecastingMedicineUIAdapter med : meds){
			for(ForecastingOrder ord : med.getFcMedicineObj().getOrders()){
				if(!ord.getBatch().isExclude()){
					if(ord.getBatch().getExpired().compare(ord.getArrived())<=0){
						ret = med.getMedicine().getNameForDisplay() +". " + Messages.getString("Error.Validation.OrderSave.ExpireDate");
						break;
					}
				}
			}
		}
		return ret;
	}



	/**
	 * Author and authors place can't be empty
	 * @return
	 */
	private String checkAuthor() {
		if(forecast.getAddress().length() == 0 || forecast.getCalculator().length() == 0){
			return Messages.getString("Error.forecasting.validation_1");
		}
		return "";
	}
	
	/**
	 * Check only end date
	 * @param newValue new Calendar or Date value for end date
	 * @param forecastUI the forecast
	 * @return error string if error, empty string if OK
	 */
	public static String checkEndDate(Object newValue, ForecastUIAdapter forecastUI) {
		if(forecastUI.getIniDate() != null && newValue != null && forecastUI.getLeadTime()!=null){
			LocalDate iniDate = DateUtils.convert(forecastUI.getReferenceDate());
					//new LocalDate(forecastUI.getReferenceDate());
			LocalDate newDate = null;
			
			if(newValue instanceof Date) {
				Date d = (Date)newValue;
				newDate = DateUtils.convert(d);
			}else if(newValue instanceof GregorianCalendar) {
				GregorianCalendar gc = (GregorianCalendar)newValue;
				newDate = DateUtils.convert(gc.getTime());
			}else
				return Messages.getString("Error.forecasting.enddate");
			
			LocalDate minDate = iniDate.plusMonths(forecastUI.getLeadTime());
			if(newDate.isAfter(minDate)){
				return "";
			}else{
				return Messages.getString("Error.forecasting.enddate");
			}
		}else{
			return "";
		}
	}
	/**
	 * Check only inventory date
	 * @param newValue new Calendar or Date value for Inventory date
	 * @param forecastUI the forecast
	 * @return error string if error, empty string if OK
	 */
	public static String checkInventoryDate(Object newValue, ForecastUIAdapter forecastUI) {
		LocalDate todayL = LocalDate.now();
				//new LocalDate(new Date());
		if(newValue==null){
			forecastUI.setReferenceDt(Date.from(todayL.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			return "";
		}else{
			LocalDate newDateL = null;
			
			if(newValue instanceof Date) {
				Date d = (Date)newValue;
				newDateL = DateUtils.convert(d);
			}else if(newValue instanceof GregorianCalendar) {
				GregorianCalendar gc = (GregorianCalendar)newValue;
				newDateL = DateUtils.convert(gc.getTime());
			}else
				return Messages.getString("Error.forecasting.refDateInFuture");

			if(newDateL.isAfter(todayL)){
				return Messages.getString("Error.forecasting.refDateInFuture");
			}else{
				return "";
			}
		}
	}

	/**
	 * get all general warnings (warnings about parameters)
	 * @return
	 */
	public List<String> warningsAsList() {
		List<String> ret = new ArrayList<String>();
	
		String s = checkLeadTime();
		if(s.length() > 0){
			ret.add(s);
		}
		
		String s1 = ForecastUIVerify.checkInventoryDate(getForecast().getReferenceDate(), getForecast());
		if(s1.length() > 0){
			ret.add(s1);
		}
		
		//warning min stock is zero
		if(getForecast().getMinStock() == 0 || getForecast().getMaxStock()==0){
			ret.add(Messages.getString("Forecast.warning.minmaxstock"));
		}

		// check expire dates in batches. May be absent in a slice
		for(ForecastingMedicineUIAdapter fMui : getForecast().getMedicines()){
			for(ForecastingBatchUIAdapter fBatch : fMui.getBatchesToExpire()){
				if(!fBatch.isExclude()){
					if (fBatch.getExpiredDtEdit() == null){
						ret.add(fMui.getMedicine().getNameForDisplayWithAbbrev() + " "+
								Messages.getString("Forecast.warning.batchexpire"));
					}
				}
			}
		}

		return ret;
	}

}
