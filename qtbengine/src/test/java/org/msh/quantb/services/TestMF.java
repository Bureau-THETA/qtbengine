package org.msh.quantb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.Test;
import org.msh.quantb.model.forecast.Forecast;
import org.msh.quantb.model.mvp.ModelFactory;

import jakarta.xml.bind.JAXBException;

public class TestMF {
	
	@Test
	public void shouldLoadFromFileAndStream() throws IOException {
			ModelFactory modelFactory = ModelFactory.of("");
			Forecast forecast = modelFactory.createForecasting("Test");
			
			forecast.setReferenceDate(modelFactory.getNow());
			forecast.setLeadTime(6);
			
			System.out.println(forecast);
			
			
			/*Forecast forecast = modelFactory.readForecasting("src/test/resources/test/", "TestForecast.qtb");
			
			forecast.setComment("test");
			forecast.setCalculator("UserFIO");
			
			modelFactory.storeForecastFull(forecast, "src/test/resources/test/");
			
			
			
			// создаем новый
			Forecast forecast = new Forecast();
			// данные из первой вкладки
			forecast.set("Данные первой вкладки")
			// сохраняем
			modelFactory.storeForecastFull(forecast, "src/test/resources/test/");
			
			*/
			
			
			
			
			
			
			
			
	
	}
}