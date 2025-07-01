package org.theta.model;

import java.util.List;

import org.msh.quantb.model.forecast.ForecastingMedicine;
import org.msh.quantb.model.forecast.ForecastingRegimen;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for handling multiple ForecastingMedicine objects in JAXB processing.
 */
@XmlRootElement
public class ForecastingResultsWrapper {
    private List<ForecastingMedicine> medicines;
    private List<ForecastingRegimen> regimes;

    @XmlElement(name = "ForecastingMedicine")
    public List<ForecastingMedicine> getMedicines() {
        return medicines;
    }
    
    @XmlElement(name = "ForecastingRegimen")
    public List<ForecastingRegimen> getRegimes() {
        return regimes;
    }

    public void setMedicines(List<ForecastingMedicine> medicines) {
        this.medicines = medicines;
    }

	public void setRegimes(List<ForecastingRegimen> regimes) {
		this.regimes = regimes;
	}
    
}
