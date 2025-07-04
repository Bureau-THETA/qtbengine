//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.medicine;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.msh.quantb.model.gen.Medicine;
import org.msh.quantb.model.gen.SimpleStamp;


/**
 * <p>Java class for Medicines complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="Medicines">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="medicines" type="{http://www.msh.org/quantb/model/gen}Medicine" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "Medicines", propOrder = {
    "medicines",
    "stamp"
})
public class Medicines {

    protected List<Medicine> medicines;
    @XmlElement(required = true)
    protected SimpleStamp stamp;

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
     * {@link Medicine }
     * 
     * 
     * @return
     *     The value of the medicines property.
     */
    public List<Medicine> getMedicines() {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }
        return this.medicines;
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
