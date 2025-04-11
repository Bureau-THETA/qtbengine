package org.stoptb.quantbcalc;

import java.util.ArrayList;
import java.util.List;

import org.msh.quantb.model.gen.DeliveryScheduleEnum;
import org.msh.quantb.services.calc.ConsumptionMonth;
import org.msh.quantb.services.io.MedicineUIAdapter;

/**
 * Medicines consumptions and deliveries for the schedule 
 * @author alexk
 *
 */
public class ScheduledConsumption {
	private DeliveryScheduleEnum schedule;
	private MedicineUIAdapter medicine;
	private List<ConsumptionMonth> consumptions = new ArrayList<ConsumptionMonth>();			//traditional QuanTB consumptions
	private List<ConsumptionMonth> consumptionsPeriod = new ArrayList<ConsumptionMonth>();	//consumptions grounded on periods for Collector
	
	public DeliveryScheduleEnum getSchedule() {
		return schedule;
	}
	public void setSchedule(DeliveryScheduleEnum schedule) {
		this.schedule = schedule;
	}
	public MedicineUIAdapter getMedicine() {
		return medicine;
	}
	public void setMedicine(MedicineUIAdapter medicine) {
		this.medicine = medicine;
	}
	public List<ConsumptionMonth> getConsumptions() {
		return consumptions;
	}
	public void setConsumptions(List<ConsumptionMonth> consumptions) {
		this.consumptions = consumptions;
	}
	
	public List<ConsumptionMonth> getConsumptionsPeriod() {
		return consumptionsPeriod;
	}
	public void setConsumptionsPeriod(List<ConsumptionMonth> consumptionsPeriod) {
		this.consumptionsPeriod = consumptionsPeriod;
	}
	/**
	 * Convenient method to create it
	 * @param med
	 * @param schedule
	 * @return
	 */
	public static ScheduledConsumption of(MedicineUIAdapter med, DeliveryScheduleEnum schedule) {
		ScheduledConsumption ret = new ScheduledConsumption();
		ret.setMedicine(med);
		ret.setSchedule(schedule);
		return ret;
	}
	

}
