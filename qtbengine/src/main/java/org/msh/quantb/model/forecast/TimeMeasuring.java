//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.forecast;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeMeasuring.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="TimeMeasuring">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="DAYS"/>
 *     <enumeration value="MONTHS"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "TimeMeasuring")
@XmlEnum
public enum TimeMeasuring {

    DAYS,
    MONTHS;

    public String value() {
        return name();
    }

    public static TimeMeasuring fromValue(String v) {
        return valueOf(v);
    }

}
