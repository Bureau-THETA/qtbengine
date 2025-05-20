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
 * Main class to run the basic example 
 */
public class Example {

	/**
	 * Main method to run from command line. Mainly for performance assessment
	 * purpose
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			File dir = new File(args[0]);
			if (dir.isDirectory()) {
				File[] directoryListing = dir.listFiles();
				if (directoryListing != null && directoryListing.length>0) {
					Date prevTime = new Date();
					Date start = new Date();
					int count = 0;
					File log = new File("log.txt");
					PrintWriter logStream = new PrintWriter(log);
					for (File child : directoryListing) {
						if (child.getName().toUpperCase().endsWith(".QTB")) {
							try {
								FileInputStream stream = new FileInputStream(child);
								log("Processing " + child.getName(), logStream);
								Forecast forecast = Calculator.instanceOf().loadForecastFromStream(stream, child.getName());
								forecast = BasicEngine.calculateDailyForecast(forecast);
								log("processed", logStream);
								prevTime = printDiag(prevTime, logStream);
								logResults(forecast, 2,logStream);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (JAXBException e) {
								e.printStackTrace();
							}
						}
						count++;
					}
					Date endTime = new Date();
					log("Total elapsed " + ((endTime.getTime() - start.getTime()) / 1000) + " sec", logStream);
					log("Processed " + count + " forecasts", logStream);
					log("Average calculation time is " + ((endTime.getTime() - start.getTime()) / 1000) / count
							+ " sec", logStream);
					logStream.flush();
					logStream.close();
				} else {
					System.err.println(
							"Usage: quantbcalc qtb_directory, instead " + dir.getAbsolutePath());
				}
			} else {
				System.err.println("Usage: quantbcalc qtb_directory, instead " + dir.getAbsolutePath());
			}
		} else {
			System.err.println("Usage: quantbcalc qtb_directory");
		}

	}

	/**
	 * Output random lines from forecasting results to result.xml file
	 * For example only
	 * @param forecast
	 * @param linesPerMedicine
	 * @param logStream 
	 */
	private static void logResults(Forecast forecast, int linesPerMedicine,  PrintWriter logStream) {
		try {
			// Convert list to XML byte array
	        byte[] xmlBytes;
			xmlBytes = ForecastingMedicineConverter.convertToXmlBytes(forecast.getMedicines());
			// Store in result.xml
	        ForecastingMedicineConverter.storeXmlFile(xmlBytes, "result.xml");
	        System.out.println("XML file saved successfully.");
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}


	/**
	 * Log it to console and file
	 * 
	 * @param message
	 * @param logStream
	 */
	private static void log(String message, PrintWriter logStream) {
		System.out.println(message);
		logStream.println(message);
	}

	/**
	 * Print diagnostic data
	 * 
	 * @param prevTime  when printDiag has been called before.
	 * @param logStream
	 */
	private static Date printDiag(Date prevTime, PrintWriter logStream) {
		int mb = 1024 * 1024;
		log("<performance>",logStream);
		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		log("  ##### Heap utilization statistics [MB] #####", logStream);

		// Print used memory
		log("  Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb, logStream);

		// Print free memory
		log("  Free Memory:" + runtime.freeMemory() / mb, logStream);

		// Print total available memory
		log("  Total Memory:" + runtime.totalMemory() / mb, logStream);

		// Print Maximum available memory
		log("  Max Memory:" + runtime.maxMemory() / mb, logStream);

		// Print time elapsed
		Date ret = new Date();
		log("  Elapsed " + ((ret.getTime() - prevTime.getTime()) / 1000) + " sec", logStream);
		log("</performance>",logStream);
		return ret;
	}
}
