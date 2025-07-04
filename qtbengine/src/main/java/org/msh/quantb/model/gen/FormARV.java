//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.gen;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for formARV.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="formARV">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="UNKNOWN"/>
 *     <enumeration value="TABLETS"/>
 *     <enumeration value="TABLETS_FDC"/>
 *     <enumeration value="SOLUTION"/>
 *     <enumeration value="CAPSULES"/>
 *     <enumeration value="SOLUBLE_TABLETS "/>
 *     <enumeration value="ORAL GRANULES"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "formARV")
@XmlEnum
public enum FormARV {

    UNKNOWN("UNKNOWN"),
    TABLETS("TABLETS"),
    TABLETS_FDC("TABLETS_FDC"),
    SOLUTION("SOLUTION"),
    CAPSULES("CAPSULES"),
    @XmlEnumValue("SOLUBLE_TABLETS ")
    SOLUBLE_TABLETS("SOLUBLE_TABLETS "),
    @XmlEnumValue("ORAL GRANULES")
    ORAL_GRANULES("ORAL GRANULES");
    private final String value;

    FormARV(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormARV fromValue(String v) {
        for (FormARV c: FormARV.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
