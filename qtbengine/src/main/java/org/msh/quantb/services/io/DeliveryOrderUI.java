package org.msh.quantb.services.io;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.msh.quantb.services.calc.DateUtils;
import org.msh.quantb.services.calc.Period;
import org.msh.quantb.services.mvp.Messages;

/**
 * One delivery order
 * All delivery orders are delivery schedule
 * @author Alex Kurasoff
 *
 */
public class DeliveryOrderUI extends AbstractUIAdapter implements Comparable<DeliveryOrderUI> {
	MonthUIAdapter orderDate;
	MonthUIAdapter deliveryDate;
	BigDecimal addCost = BigDecimal.ZERO;
	List<DeliveryOrderItemUI> items= new ArrayList<DeliveryOrderItemUI>();
	private Calendar firstFCDate;
	/**
	 * Initialize new delivery order based on delivery date and order date
	 * Some dependences may be exists between order and delivery, but logic of this dependency is not this class competence
	 * @param orderDate 
	 * @param deliveryDate
	 * @param referenceDate 
	 */
	public DeliveryOrderUI(MonthUIAdapter orderDate, MonthUIAdapter deliveryDate, Calendar referenceDate) {
		super();
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.firstFCDate = referenceDate;
	}


	/**
	 * For testing
	 * @param orderDate
	 * @param deliveryDate
	 */
	public DeliveryOrderUI(MonthUIAdapter orderDate, MonthUIAdapter deliveryDate) {
		super();
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
	}



	public Calendar getFirstFCDate() {
		return firstFCDate;
	}



	public void setFirstFCDate(Calendar firstFCDate) {
		this.firstFCDate = firstFCDate;
	}



	private MonthUIAdapter getOrderDate() {
		return orderDate;
	}
	/**
	 * Only for screen representation
	 * @return
	 */
	public String getOrderDateStr(){
		LocalDate dt = LocalDate.of(getOrderDate().getYear(),getOrderDate().getMonth()+1,1);
		dt = dt.minusDays(1); // do not confuse users!
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM, dd yyyy");
		String formattedString = dt.format(formatter);
		if(isOrderDateInPast()){
			String s = Messages.getString("DeliveryOrder.labels.asap");
			return s +" " + formattedString + ")";
			//return s +" " + dt.toString("MMM, dd yyyy") + ")";
		}else{
			return formattedString;
			//return dt.toString("MMM, dd yyyy");
		}
	}
	

	/**
	 * Only for screen representation
	 * @return
	 */
	public String getDeliveryDateStr(){
		LocalDate dt = LocalDate.of(getDeliveryDate().getYear(), getDeliveryDate().getMonth()+1,1);
		LocalDate fcDate = DateUtils.convert(getFirstFCDate());
				//new LocalDate(getFirstFCDate());
		dt = dt.minusDays(1); // do not confuse users!
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM, dd yyyy");
		//String formattedString = dt.format(formatter);
		if(isOrderDateInPast()){
			/*String dDStr = dt.toString("MMM, dd yyyy");
			if(dt.isBefore(fcDate)){
				dDStr= fcDate.toString("MMM, dd yyyy");
			}*/
			String dDStr = dt.format(formatter);
			if(dt.isBefore(fcDate)){
				dDStr= fcDate.format(formatter);
			}
			return Messages.getString("DeliveryOrder.labels.asap.delivery")+" " + dDStr + ")";
		}else{
			return dt.format(formatter);
			//return dt.toString("MMM, dd yyyy");
		}
	}


	/**
	 * Is order date before reference date +  a day?
	 * @return
	 */
	private boolean isOrderDateInPast() {
		LocalDate rd = DateUtils.convert(getFirstFCDate());
				//new LocalDate(getFirstFCDate());
		LocalDate od = DateUtils.convert(getOrderDate().getAnyDate(rd.getDayOfMonth()));
				//new LocalDate(getOrderDate().getAnyDate(rd.getDayOfMonth()));
		return od.isBefore(rd);
	}

	public void setOrderDate(MonthUIAdapter orderDate) {
		MonthUIAdapter oldValue = getOrderDate();
		this.orderDate = orderDate;
		firePropertyChange("orderDate", oldValue, getOrderDate());
	}
	public MonthUIAdapter getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(MonthUIAdapter deliveryDate) {
		MonthUIAdapter oldValue = getDeliveryDate();
		this.deliveryDate = deliveryDate;
		firePropertyChange("deliveryDate", oldValue, getDeliveryDate());
	}
	public BigDecimal getAddCost() {
		return addCost;
	}
	public void setAddCost(BigDecimal addCost) {
		BigDecimal oldValue = getAddCost();
		this.addCost = addCost;
		firePropertyChange("addCost", oldValue, getAddCost());
	}

	public List<DeliveryOrderItemUI> getItems() {
		return items;
	}
	public void setItems(List<DeliveryOrderItemUI> items) {
		this.items = items;
	}
	/**
	 * Add item to the delivery Simple add or merge if same medicine exists
	 * @param item
	 */
	public void addItem(DeliveryOrderItemUI item) {
		for(DeliveryOrderItemUI it : getItems()){
			if(it.getMedicine().compareTo(item.getMedicine()) == 0){
				if(it.getPackSize().equals(item.getPackSize())){
					it.setQuantity(it.getQuantity().add(item.getQuantity()));
					return;
				}
			}
		}
		getItems().add(item); // not found, add new one
	}

	/**
	 * Get cost of all medicines in the delivery
	 * @return
	 */
	public BigDecimal getMedCost() {
		BigDecimal res = BigDecimal.ZERO;
		res = res.setScale(4, RoundingMode.HALF_UP);
		for(DeliveryOrderItemUI item : getItems()){
			res = res.add(item.getCost());
		}
		return res.setScale(2, RoundingMode.HALF_UP);
	}
	@Override
	public String toString() {
		return "DeliveryOrderUI [deliveryDate=" + deliveryDate +", medTotal="+ getMedCost() + " items=" + items + ", getMedCost()=" + getMedCost()
		+ "]";
	}
	/**
	 * Calc total delivery cost
	 * @return
	 */
	public BigDecimal calcTotal() {
		BigDecimal res = BigDecimal.ZERO;
		res = res.setScale(4, RoundingMode.HALF_UP);
		res = res.add(getMedCost()).add(getAddCost());
		return res.setScale(2, RoundingMode.HALF_UP);
	}

	public String getFormattedMedCost(){
		return formatCost(getMedCost());
	}

	public String getFormattedAddCost(){
		return formatCost(getAddCost());
	}

	public String getFormattedDelivery(){
		return formatCost(calcTotal());
	}

	/**
	 * Format cost as string
	 * @param cost
	 * @return
	 */
	private String formatCost(BigDecimal cost) {
		BigDecimal res = cost.setScale(2, RoundingMode.HALF_UP);
		DecimalFormat formatter = new DecimalFormat("###,###,###,##0.00");
		return  formatter.format(cost);
	}
	@Override
	public int compareTo(DeliveryOrderUI o) {
		return this.getDeliveryDate().compareTo(o.getDeliveryDate());
	}
	/**
	 * get a period for this delivery
	 * @param ri
	 * @return null if not found
	 */
	public Period period(List<Period> ri) {
		for(Period r :ri) {
			if(this.getDeliveryDate().compareTo(r.getFrom())==0) {
				return r;
			}
		}
		return null;
	}




}
