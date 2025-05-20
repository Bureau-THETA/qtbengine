package org.theta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import org.msh.quantb.model.forecast.Forecast;
import org.stoptb.quantbcalc.Calculator;
import org.theta.model.ForecastingMedicineConverter;
import org.theta.services.BasicEngine;

import jakarta.xml.bind.JAXBException;

/**
 * Main class to run the basic example for processing forecasting medicine data.
 */
public class Example {

    /**
     * Main method to execute processing from the command line.
     * Used primarily for performance assessment.
     * 
     * @param args Command-line arguments, expected to contain a directory path.
     * @throws IOException If an I/O error occurs while handling files.
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            // Validate provided argument as a directory
            File dir = new File(args[0]);
            if (dir.isDirectory()) {
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null && directoryListing.length > 0) {
                    Date prevTime = new Date();
                    Date start = new Date();
                    int count = 0;
                    
                    // Initialize a log file to store processing details
                    File log = new File("log.txt");
                    PrintWriter logStream = new PrintWriter(log);
                    
                    // Iterate over each file in the directory
                    for (File child : directoryListing) {
                        if (child.getName().toUpperCase().endsWith(".QTB")) {
                            try {
                                // Load forecast data from the input file
                                FileInputStream stream = new FileInputStream(child);
                                log("Processing " + child.getName(), logStream);
                                Forecast forecast = Calculator.instanceOf().loadForecastFromStream(stream, child.getName());

                                // Perform daily forecast calculations
                                forecast = BasicEngine.calculateDailyForecast(forecast);
                                log("processed", logStream);

                                // Log memory and execution diagnostics
                                prevTime = printDiag(prevTime, logStream);

                                // Save processed forecast results
                                logResults(forecast, 2, logStream);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (JAXBException e) {
                                e.printStackTrace();
                            }
                        }
                        count++;
                    }
                    
                    // Calculate total processing time
                    Date endTime = new Date();
                    log("Total elapsed " + ((endTime.getTime() - start.getTime()) / 1000) + " sec", logStream);
                    log("Processed " + count + " forecasts", logStream);
                    log("Average calculation time is " + ((endTime.getTime() - start.getTime()) / 1000) / count + " sec", logStream);
                    
                    // Finalize logging output
                    logStream.flush();
                    logStream.close();
                } else {
                    System.err.println("Usage: quantbcalc qtb_directory, instead " + dir.getAbsolutePath());
                }
            } else {
                System.err.println("Usage: quantbcalc qtb_directory, instead " + dir.getAbsolutePath());
            }
        } else {
            System.err.println("Usage: quantbcalc qtb_directory");
        }
    }

    /**
     * Logs processing results to an XML file for demonstration purposes.
     * 
     * @param forecast Forecast object containing the calculated data.
     * @param linesPerMedicine Number of lines per medicine entry (for example use).
     * @param logStream Stream to log output messages.
     */
    private static void logResults(Forecast forecast, int linesPerMedicine, PrintWriter logStream) {
        try {
            // Convert medicine list to XML byte array
            byte[] xmlBytes = ForecastingMedicineConverter.convertToXmlBytes(forecast.getMedicines());

            // Store XML data in result.xml
            ForecastingMedicineConverter.storeXmlFile(xmlBytes, "result.xml");

            System.out.println("XML file saved successfully.");
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs the given message to both console and file.
     * 
     * @param message The message to be logged.
     * @param logStream The output stream to write log data.
     */
    private static void log(String message, PrintWriter logStream) {
        System.out.println(message);
        logStream.println(message);
    }

    /**
     * Prints diagnostic information such as memory usage and execution time.
     * 
     * @param prevTime Previous recorded timestamp for elapsed time calculation.
     * @param logStream Log stream to store performance metrics.
     * @return The updated timestamp for further diagnostics.
     */
    private static Date printDiag(Date prevTime, PrintWriter logStream) {
        int mb = 1024 * 1024;
        log("<performance>", logStream);

        // Retrieve runtime memory details
        Runtime runtime = Runtime.getRuntime();

        log("  ##### Heap utilization statistics [MB] #####", logStream);
        log("  Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb, logStream);
        log("  Free Memory: " + runtime.freeMemory() / mb, logStream);
        log("  Total Memory: " + runtime.totalMemory() / mb, logStream);
        log("  Max Memory: " + runtime.maxMemory() / mb, logStream);

        // Log elapsed execution time
        Date ret = new Date();
        log("  Elapsed " + ((ret.getTime() - prevTime.getTime()) / 1000) + " sec", logStream);
        log("</performance>", logStream);

        return ret;
    }
}
