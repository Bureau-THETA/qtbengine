package org.theta.model;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.msh.quantb.model.forecast.ForecastingMedicine;
import org.theta.services.ForecastingMedicineWrapper;

// Import Jakarta XML Binding instead of Javax
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


/**
 * This class handles conversion of a list of ForecastingMedicine objects into
 * a byte array containing their XML representation and writes it to a file.
 */
public class ForecastingMedicineConverter {


    /**
     * Converts a list of ForecastingMedicine objects into an XML byte array.
     *
     * @param medicines The list of ForecastingMedicine objects to be serialized.
     * @return A byte array containing the XML representation.
     * @throws JAXBException If an error occurs during XML processing.
     */
    public static byte[] convertToXmlBytes(List<ForecastingMedicine> medicines) throws JAXBException {
        // Create JAXB context for ForecastingMedicineWrapper class
        JAXBContext context = JAXBContext.newInstance(ForecastingMedicineWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Pretty-print XML

        // Wrap list in a container class
        ForecastingMedicineWrapper wrapper = new ForecastingMedicineWrapper();
        wrapper.setMedicines(medicines);

        // Convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(wrapper, baos); // Marshal the wrapped object into byte array
        return baos.toByteArray();
    }

    /**
     * Stores the given XML byte array into a file.
     *
     * @param xmlBytes  The XML data to store.
     * @param filePath  The file path where XML will be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public static void storeXmlFile(byte[] xmlBytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(xmlBytes); // Write byte array to file
        }
    }
}

