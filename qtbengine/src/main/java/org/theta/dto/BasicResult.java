package org.theta.dto;

import java.time.LocalDate;

import org.msh.quantb.model.gen.Medicine;

/**
 * Basic forecasting parameters and daily forecast for each medicine
 * Suit for quick starting and for 80% of all needs
 */
public class BasicResult {
	private Medicine medicine = new Medicine();
	private LocalDate date = LocalDate.now();
	private long onHand=0;
	private long missed=0;
	private long expired=0;
	private long order=0;
	private long oldCases=0;
	private long newCases=0;
	private long consOld=0;
	private long consNew=0;
	public Medicine getMedicine() {
		return medicine;
	}
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
	

}
