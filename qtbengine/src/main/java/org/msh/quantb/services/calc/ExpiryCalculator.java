package org.msh.quantb.services.calc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.ForecastingMedicineUIAdapter;
import org.msh.quantb.services.io.ForecastingResultUIAdapter;

/**
 * Prepare data for products that likely to expire
 * This data consist of three lists:
 * <ul>
 * <li> medicines that likely to expire in the next six months
 * <li> medicines that likely to expire in the six months after 
 * </ul>
 * @author alexk
 *
 */
public class ExpiryCalculator {
	private ForecastUIAdapter forecastUi;
	private List<ExpiryDetailDTO> urgent = new ArrayList<ExpiryDetailDTO>();
	private List<ExpiryDetailDTO> attention = new ArrayList<ExpiryDetailDTO>();
	private List<ExpiryDetailDTO> all = new ArrayList<ExpiryDetailDTO>();
	private LocalDate startUrgent;
	private LocalDate endUrgent;
	private LocalDate startAttention;
	private LocalDate endAttention;
	private LocalDate iniDate;
	private LocalDate endDate;
	
	
	
	
	public List<ExpiryDetailDTO> getUrgent() {
		return urgent;
	}

	
	public ForecastUIAdapter getForecastUi() {
		return forecastUi;
	}


	public void setForecastUi(ForecastUIAdapter forecastUi) {
		this.forecastUi = forecastUi;
	}


	public List<ExpiryDetailDTO> getAttention() {
		return attention;
	}


	public void setAttention(List<ExpiryDetailDTO> attention) {
		this.attention = attention;
	}


	public List<ExpiryDetailDTO> getAll() {
		return all;
	}


	public void setAll(List<ExpiryDetailDTO> all) {
		this.all = all;
	}


	public LocalDate getStartUrgent() {
		return startUrgent;
	}


	public void setStartUrgent(LocalDate startUrgent) {
		this.startUrgent = startUrgent;
	}


	public LocalDate getEndUrgent() {
		return endUrgent;
	}


	public void setEndUrgent(LocalDate endUrgent) {
		this.endUrgent = endUrgent;
	}


	public LocalDate getStartAttention() {
		return startAttention;
	}


	public void setStartAttention(LocalDate startAttention) {
		this.startAttention = startAttention;
	}


	public LocalDate getEndAttention() {
		return endAttention;
	}


	public void setEndAttention(LocalDate endAttention) {
		this.endAttention = endAttention;
	}


	public LocalDate getIniDate() {
		return iniDate;
	}


	public void setIniDate(LocalDate iniDate) {
		this.iniDate = iniDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public void setUrgent(List<ExpiryDetailDTO> urgent) {
		this.urgent = urgent;
	}


	/**
	 * Calculate all data
	 * @param list list of forecasting results
	 */
	public void calc(List<ForecastingMedicineUIAdapter> list) {
		calcAllDates();
		for(ForecastingMedicineUIAdapter mc : list) {
			String medName = mc.getMedicine().getNameForDisplayWithAbbrev();
			for( ForecastingResultUIAdapter cm : mc.getResults()) {
				calcEndDate(DateUtils.convert(cm.getFrom()));
				Long expired = cm.getExpired();
				if(expired>0) {
					LocalDate expiDate = DateUtils.convert(cm.getFrom());
					//if(expiDate.isAfter(LocalDate.now())) {		2021-06-05 !!!
						ExpiryDetailDTO dto = ExpiryDetailDTO.of(medName, expired, expiDate.minusDays(1)); //forecast on the next day
						getAll().add(dto);
						if(expiDate.isAfter(getStartUrgent().minusDays(1)) && expiDate.isBefore(getEndUrgent().plusDays(1))) {
							getUrgent().add(dto);
						}
						if(expiDate.isAfter(getStartAttention().minusDays(1)) && expiDate.isBefore(getEndAttention().plusDays(1))) {
							getAttention().add(dto);
						}
				}
			}
		}
	}
	/**
	 * Calculate all dates
	 */
	private void calcAllDates() {
		LocalDate inventory = getForecastUi().getReferenceDt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//new LocalDate(getForecastUi().getReferenceDt());
		LocalDate endDate = getForecastUi().getEndDt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				//new LocalDate(getForecastUi().getEndDt());
		setIniDate(inventory.plusDays(1));
		setEndDate(endDate);
		setStartUrgent(getIniDate());
		setEndUrgent(getStartUrgent().plusMonths(6));
		setStartAttention(getEndUrgent().plusDays(1));
		setEndAttention(getEndDate());
	}


	/**
	 * end of forecast date is max date
	 * @param thisDate
	 */
	private void calcEndDate(LocalDate thisDate) {
		if(getEndDate().isBefore(thisDate)) {
			setEndDate(thisDate);
		}
	}
	/**
	 * The best way to create ready to use calculator
	 * @param FUi
	 * @return
	 */
	public static ExpiryCalculator of(ForecastUIAdapter fUi) {
		List<ForecastingMedicineUIAdapter> medicines = fUi.getMedicines();
		ExpiryCalculator ret = new ExpiryCalculator();
		ret.setForecastUi(fUi);
		ret.calc(medicines);
		return ret;
	}
}
