package org.theta.model;

import java.util.List;

import org.msh.quantb.model.forecast.ForecastingMedicine;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for handling multiple ForecastingMedicine objects in JAXB processing.
 */
@XmlRootElement
public class ForecastingMedicineWrapper {
    private List<ForecastingMedicine> medicines;

    @XmlElement(name = "ForecastingMedicine")
    public List<ForecastingMedicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<ForecastingMedicine> medicines) {
        this.medicines = medicines;
    }
}
