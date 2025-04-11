package org.msh.quantb.services.calc;

import java.time.LocalDate;

/**
 * DTO for a medicine expiration detail data
 * @author alexk
 *
 */

public class ExpiryDetailDTO {
	private String medicine="";
	private Long quantity=0L;
	private LocalDate expired = LocalDate.now();


	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public LocalDate getExpired() {
		return expired;
	}
	public void setExpired(LocalDate expired) {
		this.expired = expired;
	}
	public static ExpiryDetailDTO of(String medName, Long quantity, LocalDate expDate) {
		ExpiryDetailDTO ret = new ExpiryDetailDTO();
		ret.setExpired(expDate);
		ret.setMedicine(medName);
		ret.setQuantity(quantity);
		return ret;
	}
	
}
