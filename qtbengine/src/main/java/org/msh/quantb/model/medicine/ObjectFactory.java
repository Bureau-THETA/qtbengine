//
// This file was generated by the Eclipse Implementation of JAXB, v4.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//


package org.msh.quantb.model.medicine;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.msh.quantb.model.medicine package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Medicines_QNAME = new QName("http://www.msh.org/quantb/model/medicine", "medicines");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.msh.quantb.model.medicine
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Medicines }
     * 
     * @return
     *     the new instance of {@link Medicines }
     */
    public Medicines createMedicines() {
        return new Medicines();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Medicines }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Medicines }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.msh.org/quantb/model/medicine", name = "medicines")
    public JAXBElement<Medicines> createMedicines(Medicines value) {
        return new JAXBElement<>(_Medicines_QNAME, Medicines.class, null, value);
    }

}
