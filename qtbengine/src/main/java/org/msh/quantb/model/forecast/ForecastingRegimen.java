//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.forecast;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.msh.quantb.model.gen.Regimen;


/**
 * Treatment regimes
 * 			
 * 
 * <p>Java class for ForecastingRegimen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ForecastingRegimen">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="percentNewCases" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="percentCasesOnTreatment" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         <element name="regimen" type="{http://www.msh.org/quantb/model/gen}Regimen"/>
 *         <element name="results" type="{http://www.msh.org/quantb/model/forecast}ForecastingRegimenResult" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="casesOnTreatment" type="{http://www.msh.org/quantb/model/forecast}MonthQuantity" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="newCases" type="{http://www.msh.org/quantb/model/forecast}MonthQuantity" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="excludeNewCases" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="excludeCasesOnTreatment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="kit" type="{http://www.msh.org/quantb/model/forecast}KitDefinition"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastingRegimen", propOrder = {
    "percentNewCases",
    "percentCasesOnTreatment",
    "regimen",
    "results",
    "casesOnTreatment",
    "newCases",
    "excludeNewCases",
    "excludeCasesOnTreatment",
    "kit"
})
public class ForecastingRegimen {

    protected float percentNewCases;
    protected float percentCasesOnTreatment;
    @XmlElement(required = true)
    protected Regimen regimen;
    protected List<ForecastingRegimenResult> results;
    protected List<MonthQuantity> casesOnTreatment;
    protected List<MonthQuantity> newCases;
    protected boolean excludeNewCases;
    protected boolean excludeCasesOnTreatment;
    @XmlElement(required = true)
    protected KitDefinition kit;

    /**
     * Gets the value of the percentNewCases property.
     * 
     */
    public float getPercentNewCases() {
        return percentNewCases;
    }

    /**
     * Sets the value of the percentNewCases property.
     * 
     */
    public void setPercentNewCases(float value) {
        this.percentNewCases = value;
    }

    /**
     * Gets the value of the percentCasesOnTreatment property.
     * 
     */
    public float getPercentCasesOnTreatment() {
        return percentCasesOnTreatment;
    }

    /**
     * Sets the value of the percentCasesOnTreatment property.
     * 
     */
    public void setPercentCasesOnTreatment(float value) {
        this.percentCasesOnTreatment = value;
    }

    /**
     * Gets the value of the regimen property.
     * 
     * @return
     *     possible object is
     *     {@link Regimen }
     *     
     */
    public Regimen getRegimen() {
        return regimen;
    }

    /**
     * Sets the value of the regimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Regimen }
     *     
     */
    public void setRegimen(Regimen value) {
        this.regimen = value;
    }

    /**
     * Gets the value of the results property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the results property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingRegimenResult }
     * 
     * 
     * @return
     *     The value of the results property.
     */
    public List<ForecastingRegimenResult> getResults() {
        if (results == null) {
            results = new ArrayList<>();
        }
        return this.results;
    }

    /**
     * Gets the value of the casesOnTreatment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the casesOnTreatment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCasesOnTreatment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthQuantity }
     * 
     * 
     * @return
     *     The value of the casesOnTreatment property.
     */
    public List<MonthQuantity> getCasesOnTreatment() {
        if (casesOnTreatment == null) {
            casesOnTreatment = new ArrayList<>();
        }
        return this.casesOnTreatment;
    }

    /**
     * Gets the value of the newCases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the newCases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNewCases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthQuantity }
     * 
     * 
     * @return
     *     The value of the newCases property.
     */
    public List<MonthQuantity> getNewCases() {
        if (newCases == null) {
            newCases = new ArrayList<>();
        }
        return this.newCases;
    }

    /**
     * Gets the value of the excludeNewCases property.
     * 
     */
    public boolean isExcludeNewCases() {
        return excludeNewCases;
    }

    /**
     * Sets the value of the excludeNewCases property.
     * 
     */
    public void setExcludeNewCases(boolean value) {
        this.excludeNewCases = value;
    }

    /**
     * Gets the value of the excludeCasesOnTreatment property.
     * 
     */
    public boolean isExcludeCasesOnTreatment() {
        return excludeCasesOnTreatment;
    }

    /**
     * Sets the value of the excludeCasesOnTreatment property.
     * 
     */
    public void setExcludeCasesOnTreatment(boolean value) {
        this.excludeCasesOnTreatment = value;
    }

    /**
     * Gets the value of the kit property.
     * 
     * @return
     *     possible object is
     *     {@link KitDefinition }
     *     
     */
    public KitDefinition getKit() {
        return kit;
    }

    /**
     * Sets the value of the kit property.
     * 
     * @param value
     *     allowed object is
     *     {@link KitDefinition }
     *     
     */
    public void setKit(KitDefinition value) {
        this.kit = value;
    }

}
