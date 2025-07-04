//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.forecast;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import org.msh.quantb.model.gen.DeliveryScheduleEnum;
import org.msh.quantb.model.gen.RegimenTypesEnum;


/**
 * 
 * 				Input parameters and results.
 * 			
 * 
 * <p>Java class for Forecast complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Forecast">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="referenceDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         <element name="iniDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         <element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         <element name="bufferStockTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="minStock" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="maxStock" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="leadTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="recordingDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         <element name="timeMeasuring" type="{http://www.msh.org/quantb/model/forecast}TimeMeasuring"/>
 *         <element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="medicines" type="{http://www.msh.org/quantb/model/forecast}ForecastingMedicine" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="regimensType" type="{http://www.msh.org/quantb/model/gen}RegimenTypesEnum"/>
 *         <element name="regimes" type="{http://www.msh.org/quantb/model/forecast}ForecastingRegimen" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="newCases" type="{http://www.msh.org/quantb/model/forecast}MonthQuantity" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="casesOnTreatment" type="{http://www.msh.org/quantb/model/forecast}MonthQuantity" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="institution" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="calculator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="total" type="{http://www.msh.org/quantb/model/forecast}ForecastingTotalItem" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="totalR" type="{http://www.msh.org/quantb/model/forecast}ForecastingTotalItem" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="totalA" type="{http://www.msh.org/quantb/model/forecast}ForecastingTotalItem" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="totalComment1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="totalComment2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="totalComment3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="totalComment4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="isOldPercents" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="isNewPercents" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="deliverySchedule" type="{http://www.msh.org/quantb/model/gen}DeliveryScheduleEnum"/>
 *         <element name="acceleratedSchedule" type="{http://www.msh.org/quantb/model/gen}DeliveryScheduleEnum"/>
 *         <element name="scenario" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="qtbVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Forecast", propOrder = {
    "name",
    "referenceDate",
    "iniDate",
    "endDate",
    "bufferStockTime",
    "minStock",
    "maxStock",
    "leadTime",
    "recordingDate",
    "timeMeasuring",
    "comment",
    "medicines",
    "regimensType",
    "regimes",
    "newCases",
    "casesOnTreatment",
    "country",
    "institution",
    "calculator",
    "region",
    "address",
    "total",
    "totalR",
    "totalA",
    "totalComment1",
    "totalComment2",
    "totalComment3",
    "totalComment4",
    "isOldPercents",
    "isNewPercents",
    "deliverySchedule",
    "acceleratedSchedule",
    "scenario",
    "qtbVersion"
})
public class Forecast {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar referenceDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar iniDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected int bufferStockTime;
    protected int minStock;
    protected int maxStock;
    protected int leadTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar recordingDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TimeMeasuring timeMeasuring;
    protected String comment;
    protected List<ForecastingMedicine> medicines;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RegimenTypesEnum regimensType;
    protected List<ForecastingRegimen> regimes;
    protected List<MonthQuantity> newCases;
    protected List<MonthQuantity> casesOnTreatment;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String institution;
    @XmlElement(required = true)
    protected String calculator;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String address;
    protected List<ForecastingTotalItem> total;
    protected List<ForecastingTotalItem> totalR;
    protected List<ForecastingTotalItem> totalA;
    @XmlElement(required = true)
    protected String totalComment1;
    @XmlElement(required = true)
    protected String totalComment2;
    @XmlElement(required = true)
    protected String totalComment3;
    @XmlElement(required = true)
    protected String totalComment4;
    protected boolean isOldPercents;
    protected boolean isNewPercents;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DeliveryScheduleEnum deliverySchedule;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DeliveryScheduleEnum acceleratedSchedule;
    protected boolean scenario;
    @XmlElement(required = true)
    protected String qtbVersion;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the referenceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReferenceDate() {
        return referenceDate;
    }

    /**
     * Sets the value of the referenceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReferenceDate(XMLGregorianCalendar value) {
        this.referenceDate = value;
    }

    /**
     * Gets the value of the iniDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIniDate() {
        return iniDate;
    }

    /**
     * Sets the value of the iniDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIniDate(XMLGregorianCalendar value) {
        this.iniDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the bufferStockTime property.
     * 
     */
    public int getBufferStockTime() {
        return bufferStockTime;
    }

    /**
     * Sets the value of the bufferStockTime property.
     * 
     */
    public void setBufferStockTime(int value) {
        this.bufferStockTime = value;
    }

    /**
     * Gets the value of the minStock property.
     * 
     */
    public int getMinStock() {
        return minStock;
    }

    /**
     * Sets the value of the minStock property.
     * 
     */
    public void setMinStock(int value) {
        this.minStock = value;
    }

    /**
     * Gets the value of the maxStock property.
     * 
     */
    public int getMaxStock() {
        return maxStock;
    }

    /**
     * Sets the value of the maxStock property.
     * 
     */
    public void setMaxStock(int value) {
        this.maxStock = value;
    }

    /**
     * Gets the value of the leadTime property.
     * 
     */
    public int getLeadTime() {
        return leadTime;
    }

    /**
     * Sets the value of the leadTime property.
     * 
     */
    public void setLeadTime(int value) {
        this.leadTime = value;
    }

    /**
     * Gets the value of the recordingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecordingDate() {
        return recordingDate;
    }

    /**
     * Sets the value of the recordingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecordingDate(XMLGregorianCalendar value) {
        this.recordingDate = value;
    }

    /**
     * Gets the value of the timeMeasuring property.
     * 
     * @return
     *     possible object is
     *     {@link TimeMeasuring }
     *     
     */
    public TimeMeasuring getTimeMeasuring() {
        return timeMeasuring;
    }

    /**
     * Sets the value of the timeMeasuring property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeMeasuring }
     *     
     */
    public void setTimeMeasuring(TimeMeasuring value) {
        this.timeMeasuring = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the medicines property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the medicines property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedicines().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingMedicine }
     * 
     * 
     * @return
     *     The value of the medicines property.
     */
    public List<ForecastingMedicine> getMedicines() {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }
        return this.medicines;
    }

    /**
     * Gets the value of the regimensType property.
     * 
     * @return
     *     possible object is
     *     {@link RegimenTypesEnum }
     *     
     */
    public RegimenTypesEnum getRegimensType() {
        return regimensType;
    }

    /**
     * Sets the value of the regimensType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegimenTypesEnum }
     *     
     */
    public void setRegimensType(RegimenTypesEnum value) {
        this.regimensType = value;
    }

    /**
     * Gets the value of the regimes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the regimes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegimes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingRegimen }
     * 
     * 
     * @return
     *     The value of the regimes property.
     */
    public List<ForecastingRegimen> getRegimes() {
        if (regimes == null) {
            regimes = new ArrayList<>();
        }
        return this.regimes;
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
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the institution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Sets the value of the institution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitution(String value) {
        this.institution = value;
    }

    /**
     * Gets the value of the calculator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculator() {
        return calculator;
    }

    /**
     * Sets the value of the calculator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculator(String value) {
        this.calculator = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the total property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTotal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingTotalItem }
     * 
     * 
     * @return
     *     The value of the total property.
     */
    public List<ForecastingTotalItem> getTotal() {
        if (total == null) {
            total = new ArrayList<>();
        }
        return this.total;
    }

    /**
     * Gets the value of the totalR property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the totalR property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTotalR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingTotalItem }
     * 
     * 
     * @return
     *     The value of the totalR property.
     */
    public List<ForecastingTotalItem> getTotalR() {
        if (totalR == null) {
            totalR = new ArrayList<>();
        }
        return this.totalR;
    }

    /**
     * Gets the value of the totalA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the totalA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTotalA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ForecastingTotalItem }
     * 
     * 
     * @return
     *     The value of the totalA property.
     */
    public List<ForecastingTotalItem> getTotalA() {
        if (totalA == null) {
            totalA = new ArrayList<>();
        }
        return this.totalA;
    }

    /**
     * Gets the value of the totalComment1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalComment1() {
        return totalComment1;
    }

    /**
     * Sets the value of the totalComment1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalComment1(String value) {
        this.totalComment1 = value;
    }

    /**
     * Gets the value of the totalComment2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalComment2() {
        return totalComment2;
    }

    /**
     * Sets the value of the totalComment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalComment2(String value) {
        this.totalComment2 = value;
    }

    /**
     * Gets the value of the totalComment3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalComment3() {
        return totalComment3;
    }

    /**
     * Sets the value of the totalComment3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalComment3(String value) {
        this.totalComment3 = value;
    }

    /**
     * Gets the value of the totalComment4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalComment4() {
        return totalComment4;
    }

    /**
     * Sets the value of the totalComment4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalComment4(String value) {
        this.totalComment4 = value;
    }

    /**
     * Gets the value of the isOldPercents property.
     * 
     */
    public boolean isIsOldPercents() {
        return isOldPercents;
    }

    /**
     * Sets the value of the isOldPercents property.
     * 
     */
    public void setIsOldPercents(boolean value) {
        this.isOldPercents = value;
    }

    /**
     * Gets the value of the isNewPercents property.
     * 
     */
    public boolean isIsNewPercents() {
        return isNewPercents;
    }

    /**
     * Sets the value of the isNewPercents property.
     * 
     */
    public void setIsNewPercents(boolean value) {
        this.isNewPercents = value;
    }

    /**
     * Gets the value of the deliverySchedule property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryScheduleEnum }
     *     
     */
    public DeliveryScheduleEnum getDeliverySchedule() {
        return deliverySchedule;
    }

    /**
     * Sets the value of the deliverySchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryScheduleEnum }
     *     
     */
    public void setDeliverySchedule(DeliveryScheduleEnum value) {
        this.deliverySchedule = value;
    }

    /**
     * Gets the value of the acceleratedSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryScheduleEnum }
     *     
     */
    public DeliveryScheduleEnum getAcceleratedSchedule() {
        return acceleratedSchedule;
    }

    /**
     * Sets the value of the acceleratedSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryScheduleEnum }
     *     
     */
    public void setAcceleratedSchedule(DeliveryScheduleEnum value) {
        this.acceleratedSchedule = value;
    }

    /**
     * Gets the value of the scenario property.
     * 
     */
    public boolean isScenario() {
        return scenario;
    }

    /**
     * Sets the value of the scenario property.
     * 
     */
    public void setScenario(boolean value) {
        this.scenario = value;
    }

    /**
     * Gets the value of the qtbVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQtbVersion() {
        return qtbVersion;
    }

    /**
     * Sets the value of the qtbVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQtbVersion(String value) {
        this.qtbVersion = value;
    }

}
