package org.msh.quantb.services.calc;

import org.msh.quantb.services.io.ForecastingResultUIAdapter;

/**
 * Details of a month consumption
 * Daily data
 * Does not need for any month, only for special cases
 * @author alexk
 *
 */
public class ConsumptionDetail {
	private long month;
	private long day=0;
	private long onHand=0;
	private long missed=0;
	private long expired=0;
	private long order=0;
	private long oldCases=0;
	private long newCases=0;
	private long consOld=0;
	private long consNew=0;

	
	
	public long getMonth() {
		return month;
	}



	public void setMonth(long month) {
		this.month = month;
	}



	public long getDay() {
		return day;
	}



	public void setDay(long day) {
		this.day = day;
	}



	public long getOnHand() {
		return onHand;
	}



	public void setOnHand(long onHand) {
		this.onHand = onHand;
	}



	public long getMissed() {
		return missed;
	}



	public void setMissed(long missed) {
		this.missed = missed;
	}



	public long getExpired() {
		return expired;
	}



	public void setExpired(long expired) {
		this.expired = expired;
	}



	public long getOrder() {
		return order;
	}



	public void setOrder(long order) {
		this.order = order;
	}



	public long getOldCases() {
		return oldCases;
	}



	public void setOldCases(long oldCases) {
		this.oldCases = oldCases;
	}



	public long getNewCases() {
		return newCases;
	}



	public void setNewCases(long newCases) {
		this.newCases = newCases;
	}



	public long getConsOld() {
		return consOld;
	}



	public void setConsOld(long consOld) {
		this.consOld = consOld;
	}



	public long getConsNew() {
		return consNew;
	}



	public void setConsNew(long consNew) {
		this.consNew = consNew;
	}



	/**
	 * Create daily detail from daily forecast
	 * Months from 1, not ZERO!
	 * @param resUi
	 * @return
	 */
	public static ConsumptionDetail of(ForecastingResultUIAdapter resUi) {
		ConsumptionDetail ret = new ConsumptionDetail();
		if(resUi.getFromDay()==resUi.getToDay() && resUi.getFromDay()!=0) {
			ret.setMonth(resUi.getMonth().getMonth()+1);
			ret.setDay(resUi.getFromDay());
			ret.setExpired(resUi.getExpired());
			ret.setMissed(resUi.getMissing().longValue());
			ret.setNewCases(resUi.getNewCases().longValue());
			ret.setOldCases(resUi.getOldCases().longValue());
			ret.setOnHand(resUi.getAllAvailable().longValue());
			ret.setOrder(resUi.getInOrders());
			ret.setConsNew(resUi.getConsNew().longValue());
			ret.setConsOld(resUi.getConsOld().longValue());
		}
		return ret;
	}



	@Override
	public String toString() {
		return "ConsumptionDetail [month=" + month + ", day=" + day + ", onHand=" + onHand + ", missed=" + missed
				+ ", expired=" + expired + ", order=" + order + ", oldCases=" + oldCases + ", newCases=" + newCases
				+ ", consOld=" + consOld + ", consNew=" + consNew + "]";
	}

	
}
