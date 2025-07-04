//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.regimen;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.msh.quantb.model.gen.Regimen;
import org.msh.quantb.model.gen.SimpleStamp;


/**
 * <p>Java class for Regimens complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Regimens">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="regimen" type="{http://www.msh.org/quantb/model/gen}Regimen" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="stamp" type="{http://www.msh.org/quantb/model/gen}SimpleStamp"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Regimens", propOrder = {
    "regimen",
    "stamp"
})
public class Regimens {

    protected List<Regimen> regimen;
    @XmlElement(required = true)
    protected SimpleStamp stamp;

    /**
     * Gets the value of the regimen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the regimen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegimen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Regimen }
     * 
     * 
     * @return
     *     The value of the regimen property.
     */
    public List<Regimen> getRegimen() {
        if (regimen == null) {
            regimen = new ArrayList<>();
        }
        return this.regimen;
    }

    /**
     * Gets the value of the stamp property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleStamp }
     *     
     */
    public SimpleStamp getStamp() {
        return stamp;
    }

    /**
     * Sets the value of the stamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleStamp }
     *     
     */
    public void setStamp(SimpleStamp value) {
        this.stamp = value;
    }

}
