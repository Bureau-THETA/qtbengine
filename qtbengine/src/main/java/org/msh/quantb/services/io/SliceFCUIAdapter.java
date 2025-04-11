package org.msh.quantb.services.io;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.msh.quantb.model.forecast.ForecastingBatch;
import org.msh.quantb.model.forecast.ForecastingRegimen;
import org.msh.quantb.model.forecast.ForecastingResult;
import org.msh.quantb.model.forecast.MonthQuantity;
import org.msh.quantb.model.mvp.ModelFactory;
import org.msh.quantb.services.calc.DateParser;
import org.msh.quantb.services.calc.DateUtils;
import org.msh.quantb.services.mvp.Messages;
import org.msh.quantb.view.CanShowMessages;

/**
 * Features and constrains for a slice
 * @author Alexey Kurasov
 *
 */
public class SliceFCUIAdapter extends AbstractUIAdapter {
	private ForecastUIAdapter forecast;
	private String name;
	protected Calendar referenceDate;
	private Calendar iniDate;
	private Calendar endDate;
	private Integer bufferStockTime;
	private Integer minStock;
	private Integer maxStock;
	private Integer leadTime;
	private String comment;
	private String address;
	private String institution;
	private String calculator;
	private CanShowMessages messager;
	/**
	 * Only valid constructor
	 * @param _forecast an original forecasting
	 * @param messager class to show or log or log and show info, errors and warnings, typically view factory
	 */
	public SliceFCUIAdapter(ForecastUIAdapter _forecast, CanShowMessages messager){
		this.forecast = _forecast;
		this.messager = messager;
		this.name = _forecast.getName();
		this.referenceDate = _forecast.getReferenceDate();
		this.iniDate = _forecast.getIniDate();
		this.endDate = _forecast.getEndDate();
		this.bufferStockTime = 0;
		this.minStock = _forecast.getMinStock();
		this.maxStock = _forecast.getMaxStock();
		this.leadTime = _forecast.getLeadTime();
		this.comment = _forecast.getComment();
		this.address = _forecast.getAddress();
		this.institution = _forecast.getInstitution();
		this.calculator = _forecast.getCalculator();
		addLogic();
	}

	/**
	 * Get string representation of the FC period duration
	 * @return
	 */
	public String getPeriodAsString() {
		String s = "";
		if (getReferenceDate()!=null && getEndDate()!=null){
			return DateParser.getDurationOfPeriod(getFCBeginDate(), getEndDate().getTime());
		}
		return "(" + s + ")";
	}


	/**
	 * Add some logic to the slice check and set
	 * Only reference date, lead time and end date may be changed
	 * 
	 */
	private void addLogic() {
		this.addPropertyChangeListener("referenceDate", new PropertyChangeListener() {
			private boolean allow = true;
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (allow && !isRDValid()){
					allow = false;
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							setReferenceDate((Calendar)evt.getOldValue());
						}
					});

				}else{
					allow=true; //for future check
					calcIniDate();
				}
			}
		});
		// lead time can move only reference date, not forecasting begin date as for regular forecasting
		this.addPropertyChangeListener("leadTime", new PropertyChangeListener() {
			private boolean allow = true;
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if (allow && !isRDValid()){
					allow = false;
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							setLeadTime((Integer) evt.getOldValue()); //return old value infinitive loop possible???
						}
					});

				}else{
					allow = true;
					calcIniDate();
				}

			}
		});

		// end date should be checked against iniDate
		this.addPropertyChangeListener("endDate", new PropertyChangeListener() {
			private boolean allow = true;
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				if(allow && (!isRDValid())){
					allow = false;
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							setEndDate((Calendar)evt.getOldValue());
						}
					});
				}else{
					allow=true;
				}

			}
		});

	}

	/**
	 * Reference date will be valid if it + lead time + 1 day will be inside original forecasting period
	 * @return
	 */
	private boolean isRDValid() {
		String s = ForecastUIVerify.ruleReferenceDate(getReferenceDate(), getEndDate(), getLeadTime());
		if(s.length()>0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * this end date plus this buffer stock should be not after the original end date plus the original buffer stock
	 * @return
	 */
	protected boolean isEndValid() {
		Calendar veryEnd = getForecast().getEndDate();
		veryEnd.add(Calendar.MONTH, getForecast().getBufferStockTime());
		Calendar thisVeryEnd = DateUtils.getCleanCalendar(getEndDate().get(Calendar.YEAR),
				getEndDate().get(Calendar.MONTH), getEndDate().get(Calendar.DAY_OF_MONTH));
		thisVeryEnd.add(Calendar.MONTH, getBufferStockTime());
		if(DateUtils.compareDates(thisVeryEnd,veryEnd)>0){
			getMessager().showError(Messages.getString("Forecasting.slice.error.endDate") +
					DateUtils.formatMedium(getIniDate()) + ", "+
					DateUtils.formatMedium(getEndDate()) +
					Messages.getString("Forecasting.slice.error.endDate1") +
					getBufferStockTime() +")."
					);
			return false;
		}else{
			return true;
		}
	}

	public CanShowMessages getMessager() {
		return messager;
	}



	public void setMessager(CanShowMessages messager) {
		this.messager = messager;
	}



	public ForecastUIAdapter getForecast() {
		return forecast;
	}



	public void setForecast(ForecastUIAdapter forecast) {
		ForecastUIAdapter oldValue = getForecast();
		this.forecast = forecast;
		firePropertyChange("forecast", oldValue, getForecast());
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = getName();
		this.name = name;
		firePropertyChange("name", oldValue, getName());
	}



	public Calendar getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Calendar referenceDate) {
		Calendar oldValue = getReferenceDate();
		this.referenceDate = referenceDate;
		firePropertyChange("referenceDate", oldValue, getReferenceDate());
		firePropertyChange("referenceDateD", null, getReferenceDateD());
		firePropertyChange("periodAsString", null, getPeriodAsString());
		firePropertyChange("iniDateDt", null, getIniDateDt());
	}

	public Date getReferenceDateD(){
		return getReferenceDate().getTime();
	}

	public void setReferenceDateD(Date referenceDateD){
		LocalDate tmp = referenceDateD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				//new LocalDate(referenceDateD);
		setReferenceDate(DateUtils.getCleanCalendar(tmp.getYear(), tmp.getMonthValue()-1,tmp.getDayOfMonth()));
	}

	public Calendar getIniDate() {
		return iniDate;
	}

	public void setIniDate(Calendar iniDate) {
		this.iniDate = iniDate;
	}

	public void setIniDateDt(Date iniDateD) {
		LocalDate tmp = iniDateD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				//new LocalDate(iniDateD);
		setIniDate(DateUtils.getCleanCalendar(tmp.getYear(), tmp.getMonthValue()-1,tmp.getDayOfMonth()));
	}

	public Date getIniDateDt() {
		return iniDate.getTime();
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public Date getEndDateDt() {
		return endDate.getTime();
	}


	public void setEndDate(Calendar endDate) {
		Calendar oldValue = getEndDate();
		this.endDate = endDate;
		firePropertyChange("endDate", oldValue, getEndDate());
		firePropertyChange("endDateDt", oldValue.getTime(), getEndDate().getTime());
		firePropertyChange("periodAsString", null, getPeriodAsString());
	}

	public void setEndDateDt(Date endDateD) {
		Calendar oldValue = GregorianCalendar.getInstance();
		oldValue.setTime(getEndDateDt());
		if(endDateD!=null){
			this.endDate.setTime(endDateD);
		}
		firePropertyChange("endDate", oldValue, getEndDate());
		firePropertyChange("endDateDt", oldValue.getTime(), getEndDateDt());
		firePropertyChange("periodAsString", null, getPeriodAsString());
	}

	public Integer getBufferStockTime() {
		return bufferStockTime;
	}

	public void setBufferStockTime(Integer bufferStockTime) {
		Integer oldValue = getBufferStockTime();
		this.bufferStockTime = bufferStockTime;
		firePropertyChange("bufferStockTime", oldValue, getBufferStockTime());
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		Integer oldValue = getMinStock();
		this.minStock = minStock;
		firePropertyChange("minStock", oldValue, getMinStock());
	}

	public Integer getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Integer maxStock) {
		Integer oldValue = getMaxStock();
		this.maxStock = maxStock;
		firePropertyChange("maxStoc", oldValue, getMaxStock());
	}

	public Integer getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(Integer leadTime) {
		Integer oldValue = getLeadTime();
		this.leadTime = leadTime;
		firePropertyChange("leadTime", oldValue, getLeadTime());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		String oldValue = getComment();
		this.comment = comment;
		firePropertyChange("comment", oldValue, getComment());
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		String oldValue = getAddress();
		this.address = address;
		firePropertyChange("country", oldValue, getAddress());

	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		String oldValue = getInstitution();
		this.institution = institution;
		firePropertyChange("institution", oldValue, getInstitution());
	}

	public String getCalculator() {
		return calculator;
	}

	public void setCalculator(String calculator) {
		String oldValue = getCalculator();
		this.calculator = calculator;
		firePropertyChange("calculator", oldValue, getCalculator());
	}

	/**
	 * Prepare the forecasting for a slice
	 * @return new Forecast
	 */
	public ForecastUIAdapter prepareSliceForecasting() {
		ForecastUIAdapter fcUi = copyParameters();
		copyRegimens(fcUi);
		fillQuantities(fcUi);
		fillStocks(fcUi);
		return fcUi;
	}
	/**
	 * Create new Forecast and copy parameters entered by user
	 * @return 
	 */
	private ForecastUIAdapter copyParameters() {
		ForecastUIAdapter fcUi = new ForecastUIAdapter(ModelFactory.of("").createForecasting(getName()));
		fcUi.setReferenceDate(getReferenceDate());
		fcUi.setLeadTime(getLeadTime());
		fcUi.setBufferStockTime(getBufferStockTime());
		fcUi.setMaxStock(getMaxStock());
		fcUi.setMinStock(getMinStock());
		fcUi.setIniDate(getIniDate());
		fcUi.setEndDate(getEndDate());
		fcUi.setCalculator(getCalculator());
		fcUi.setInstitution(getInstitution());
		fcUi.setAddress(getAddress());
		fcUi.setRegimensType(getForecast().getRegimensType());
		fcUi.getForecastObj().setIsOldPercents(getForecast().isEnrolledCasesPercents());
		fcUi.getForecastObj().setIsNewPercents(getForecast().isExpectedCasesPercents());
		fcUi.setQtbVersion(getForecast().getQtbVersion());
		fcUi.setComment(getComment());
		return fcUi;
	}

	/**
	 * Fill stock values from source forecasting to the slice
	 * We're needed estimated stock values on the reference date of the slice
	 * Medicines for the slice have been determined
	 * Source forecasting assumes as calculated properly 
	 * @param fcUi the slice
	 */
	private void fillStocks(ForecastUIAdapter fcUi) {
		for(ForecastingMedicineUIAdapter fMedUi : fcUi.getMedicines()){
			ForecastingMedicineUIAdapter medSrc = getForecast().getMedicine(fMedUi.getMedicine());
			int daysDiff = DateUtils.daysBetween(fcUi.getReferenceDate().getTime(), getForecast().getReferenceDate().getTime()); //20160825 seems as correct, because it is difference
			ForecastingResult resSrc = medSrc.getFcMedicineObj().getResults().get(daysDiff);
			//System.out.println(resSrc.getFromDay() + " month -" + resSrc.getMonth().getMonth() + " year-"+resSrc.getMonth().getYear());
			//create a result
			ForecastingResult resTrg = ModelFactory.of("").createForecastingResult(ModelFactory.of("").createMonth(resSrc.getMonth().getYear(), resSrc.getMonth().getMonth()));
			resTrg.setFromDay(fcUi.getFirstFCDate().get(Calendar.DAY_OF_MONTH));
			resTrg.setToDay(resTrg.getFromDay());
			fMedUi.getFcMedicineObj().getResults().add(resTrg);
			//add batches
			for(ForecastingBatch srcB : resSrc.getBatches()){
				if (srcB.getQuantityAvailable().compareTo(BigDecimal.ZERO) > 0){
					ForecastingBatchUIAdapter srcBUi = new ForecastingBatchUIAdapter(srcB);
					ForecastingBatchUIAdapter trgBUi = srcBUi.makeClone(ModelFactory.of(""));
					trgBUi.getForecastingBatchObj().setQuantity(trgBUi.getForecastingBatchObj().getQuantityAvailable().longValue());
					fMedUi.getFcMedicineObj().getBatchesToExpire().add(trgBUi.getForecastingBatchObj().getOriginal());
				}
			}
			//add orders, all orders arrived before and on RD became batches, so we needed only orders after RD
			for(ForecastingOrderUIAdapter srcO : medSrc.getOrdersByArrival()){
				if (DateUtils.compareDates(srcO.getArrived(),fcUi.getFirstFCDate())>0 && DateUtils.compareDates(fcUi.getLastDate(), srcO.getArrived())>=0){
					ForecastingOrderUIAdapter trgO = srcO.makeClone(ModelFactory.of(""));
					fMedUi.getFcMedicineObj().getOrders().add(trgO.getForecastingOrderObj());
				}
			}
		}

	}




	/**
	 * Fill monthly cases quantities from the source forecasting
	 * Both for enrolled and expected cases
	 * @param fcUi the "slice"
	 */
	private void fillQuantities(ForecastUIAdapter fcUi) {
		// 20180430 Problem will be if inventory date will not exactly end of month
		// in this case, all cases this month may be enrolled or expected, but not both
		// Make them EXPECTED, regardless of on which date month has been divided!
		//
		// 20180822 redmine issue http://redmine.inka.in.ua/issues/114
		// divide proportional
		int coeff = 1;
		if(DateUtils.isLastDayOfMonth(fcUi.getReferenceDate())){
			coeff = 0;
		}
		// for percentage style - quantities are directly in Forecasting
		//enrolled
		List<MonthQuantity> enrollPers = fcUi.getForecastObj().getCasesOnTreatment();
		for(MonthQuantity mq : getForecast().getForecastObj().getCasesOnTreatment()){
			findAndSet(mq, enrollPers);
		}
		for(MonthQuantity mq : getForecast().getForecastObj().getNewCases()){
			findAndSet(mq, enrollPers.subList(0, enrollPers.size()-coeff));
		}
		//expected
		List<MonthQuantity> expectPers = fcUi.getForecastObj().getNewCases();

		for(MonthQuantity mq : getForecast().getForecastObj().getNewCases()){
			findAndSet(mq, expectPers);
		}
		if(coeff==1){
			divideLastFirstProportional(fcUi.getReferenceDate().get(Calendar.DAY_OF_MONTH), enrollPers.get(enrollPers.size()-1), expectPers.get(0));
		}
		// for quantities style - quantities are in each ForecastingRegimen
		for(ForecastingRegimen fr :getForecast().getForecastObj().getRegimes()){
			//enrolled
			List<MonthQuantity> rEnrollQuant = findRegimen(fr, fcUi.getForecastObj().getRegimes()).getCasesOnTreatment();
			for(MonthQuantity mq : fr.getCasesOnTreatment()){
				findAndSet(mq, rEnrollQuant);
			}
			for(MonthQuantity mq : fr.getNewCases()){
				findAndSet(mq, rEnrollQuant.subList(0, rEnrollQuant.size()-coeff));
			}
			//expected
			List<MonthQuantity> rEexpectQuant = findRegimen(fr, fcUi.getForecastObj().getRegimes()).getNewCases();
			for(MonthQuantity mq : fr.getNewCases()){
				findAndSet(mq, rEexpectQuant);
			}
			if(coeff==1){
				divideLastFirstProportional(fcUi.getReferenceDate().get(Calendar.DAY_OF_MONTH), rEnrollQuant.get(rEnrollQuant.size()-1), rEexpectQuant.get(0));
			}
		}

	}

	/**
	 * Currently last enrolled should be zero, firstExpected contains full quantity of cases, dayInMonth is not the last day of month
	 * In accordance with redmine issue http://redmine.inka.in.ua/issues/114
	 * No ceil round up!
	 * @param dayInMonth day of reference date, if reference date is not last day of a month
	 * @param lastEnrolled
	 * @param firstExpected
	 */
	private void divideLastFirstProportional(int dayInMonth, MonthQuantity lastEnrolled, MonthQuantity firstExpected) {
		int allQuantity = firstExpected.getIQuantity();
		LocalDate ld = LocalDate.of(firstExpected.getMonth().getYear(), firstExpected.getMonth().getMonth()+1, 1);
		//ld = ld.dayOfMonth().withMaximumValue();
		//float days = ld.getDayOfMonth() * 1.0f;
		float days = ld.getMonth().length(ld.isLeapYear()) * 1.0f;
		double expectedf = allQuantity * (1.0-dayInMonth/days);
		int expected = (int) Math.ceil(expectedf);
		int enrolled = allQuantity-expected;
		lastEnrolled.setIQuantity(enrolled);
		firstExpected.setIQuantity(expected);
	}

	/**
	 * Find given ForecastingReregimen 
	 * @param fr given ForecastingRegimen
	 * @param list list for search
	 * @return ForecastingRegimen or null if not found
	 */
	public static ForecastingRegimen findRegimen(ForecastingRegimen fr,
			List<ForecastingRegimen> list) {
		ForecastingRegimen ret = null;
		RegimenUIAdapter givenR = new RegimenUIAdapter(fr.getRegimen());
		for(ForecastingRegimen fR : list){
			/*			if((fr.getRegimen().getName().equals(fr.getRegimen().getName())) &&
					fR.getRegimen().getFormulation().equals(fr.getRegimen().getFormulation())){
				ret=fR;
				break;*/
			RegimenUIAdapter thisR = new RegimenUIAdapter(fR.getRegimen());
			if(givenR.equals(thisR)){
				ret=fR;
				break;
			}
		}
		return ret;
	}




	/**
	 * search for month in targetMq, if will found then set quantity
	 * @param mq source month quantity
	 * @param targetMq list of target month quantities
	 */
	public static void findAndSet(MonthQuantity mq, List<MonthQuantity> targetMq) {
		for(MonthQuantity tMq : targetMq){
			if((tMq.getMonth().getYear() == mq.getMonth().getYear()) && 
					(tMq.getMonth().getMonth() == mq.getMonth().getMonth()) ){
				tMq.setIQuantity(mq.getIQuantity()+tMq.getIQuantity());
			}
		}

	}




	/**
	 * Copy regimes from source forecasting, regimes should be same!
	 * Prepare slots for quantities
	 * @param fcUi the "slice"
	 */
	private void copyRegimens(ForecastUIAdapter fcUi) {
		fcUi.setRegimensType(getForecast().getRegimensType());
		for(ForecastingRegimenUIAdapter frUi : getForecast().getRegimes()){
			RegimenUIAdapter rUi = frUi.getRegimen().makeClone();
			rUi.setName(frUi.getRegimen().getName());
			ForecastingRegimen fr = ModelFactory.of("").createForecastingRegimen(rUi.getRegimen());
			fcUi.getForecastObj().getRegimes().add(fr);
			fr.setPercentCasesOnTreatment(frUi.getFcRegimenObj().getPercentCasesOnTreatment());
			fr.setPercentNewCases(frUi.getFcRegimenObj().getPercentNewCases());
		}
		fcUi.refreshMedicinesInFc(); //create medicines list
		copyMedPacks(fcUi);
		//create enrolled cases with zero quantities
		fcUi.shiftOldCasesRegimens();
		fcUi.shiftOldCasesPercents();
		//create expected cases with zero quantities
		fcUi.shiftNewCasesRegimens();
		fcUi.shiftNewCasesPercents();
	}
	
	/**
	 * Copy pack sizes and pack prices from the original forecast
	 * @param fcUi the slice
	 */
	private void copyMedPacks(ForecastUIAdapter fcUi) {
		List<ForecastingMedicineUIAdapter> origMeds = getForecast().getMedicines();
		List<ForecastingMedicineUIAdapter> sliceMeds = fcUi.getMedicines();
		for(ForecastingMedicineUIAdapter oMed : origMeds){
			ForecastingMedicineUIAdapter sMed = findMedicine(oMed, sliceMeds);
			if(sMed != null){
				sMed.copyPacks(oMed);
			}
			
		}
		
	}
	/**
	 * Find medicine in a list given
	 * @param med - medicine to find
	 * @param listMeds
	 * @return null, if not found
	 */
	private ForecastingMedicineUIAdapter findMedicine(ForecastingMedicineUIAdapter med, List<ForecastingMedicineUIAdapter> listMeds) {
		for(ForecastingMedicineUIAdapter fm : listMeds){
			if(fm.equals(med)){
				return fm;
			}
		}
		return null;
	}

	/**
	 * Get first day after RD
	 * @return
	 */
	public Date getFCBeginDate() {
		LocalDate tmp = DateUtils.convert(getReferenceDate());
				//new LocalDate(getReferenceDate());
		tmp.plusDays(1);
		return Date.from(tmp.atStartOfDay(ZoneId.systemDefault()).toInstant());
		//return tmp.toDate();
	}
	/**
	 * Calculate init date
	 */
	public void calcIniDate() {
		LocalDate firstFC = ForecastUIAdapter.calcFirstDate(getReferenceDate());
		LocalDate iniDate = firstFC.plusMonths(getLeadTime());
		//setIniDateDt(iniDate.toDate());
		setIniDateDt(Date.from(iniDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	

}

