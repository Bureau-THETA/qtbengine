package org.stoptb.quantbcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.msh.quantb.model.gen.DeliveryScheduleEnum;
import org.msh.quantb.services.calc.DeliveryOrdersControl;
import org.msh.quantb.services.calc.Period;
import org.msh.quantb.services.io.ForecastUIAdapter;
import org.msh.quantb.services.io.MonthUIAdapter;

import jakarta.xml.bind.JAXBException;

public class CalculatorTests {

	@Test
	public void loadAndValidate() throws IOException{
		File file = new File("src/test/resources/test/Fictitia_2016_FASTER_V4.qtb");
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			ForecastUIAdapter forecast = Calculator.instanceOf().load(stream, "dummy");
			assertEquals(22, forecast.getCasesOnTreatment().size());
			List<String> err = Calculator.instanceOf().validate(forecast);
			assertEquals(0, err.size());
			//make fc bad three times
			forecast.getForecastObj().setMinStock(1000);
			forecast.getForecastObj().getRegimes().clear();
			List<String> err1 = Calculator.instanceOf().validate(forecast);
			assertEquals(3, err1.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void loadAndCalculate() throws IOException{
		File file = new File("src/test/resources/test/Fictitia_2016_FASTER_V4.qtb");
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			ForecastUIAdapter forecast = Calculator.instanceOf().load(stream, "dummy");
			//traditional, deliveries in the past are allowed
			ForecastResult result = Calculator.instanceOf().calculate(forecast);
			assertEquals(50, result.getMonthlySummary().size());
			assertEquals(5851, result.getMonthlySummary().get(0).getConsumptions().get(0).getDelivery().longValue());
			assertEquals(130900,result.getMonthlySummary().get(32).getConsumptions().get(0).getDelivery().longValue());
			assertEquals(306100,result.getMonthlySummary().get(32).getConsumptions().get(9).getDelivery().longValue());
			//colletctor's way, deliveries in the past are not allowed
			forecast.setCollector(true);
			result = Calculator.instanceOf().calculate(forecast);
			assertEquals(50, result.getMonthlySummary().size());
			assertEquals(0, result.getMonthlySummary().get(0).getConsumptions().get(0).getDelivery().longValue());
			assertEquals(0,result.getMonthlySummary().get(32).getConsumptions().get(0).getDelivery().longValue());
			assertEquals(0,result.getMonthlySummary().get(32).getConsumptions().get(9).getDelivery().longValue());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void loadAndStoreToBytes() throws JAXBException, IOException{
		File file = new File("src/test/resources/test/Fictitia_2016_FASTER_V4.qtb");
		FileInputStream stream;
		stream = new FileInputStream(file);
		ForecastUIAdapter forecast = Calculator.instanceOf().load(stream, "dummy");
		byte[] result = Calculator.instanceOf().storeToBytes(forecast.getForecastObj());
		InputStream iStream =  new ByteArrayInputStream(result);
		StringWriter writer = new StringWriter();
		IOUtils.copy(iStream, writer,  "UTF-8");
		String theString = writer.toString();
		assertTrue(theString.contains("<ns2:arrived>2016-09-30</ns2:arrived>"));
	}
	@Test
	public void regularIntervals() {
		// Prepare test dates
		//[Sep-22, Oct-22, Nov-22, Dec-22, Jan-23, Feb-23, Mar-23, Apr-23, May-23, Jun-23, 
		//Jul-23, Aug-23, Sep-23, Oct-23, Nov-23, Dec-23, Jan-24, Feb-24, Mar-24, Apr-24, 
		//May-24, Jun-24, Jul-24, Aug-24, Sep-24, Oct-24, Nov-24, Dec-24]
		List<MonthUIAdapter> deliveryDates = new ArrayList<MonthUIAdapter>();
		LocalDate start = LocalDate.of(2022, 9, 1);// new LocalDate("2022-09-01");
		LocalDate fin = LocalDate.of(2025, 1, 1);//new LocalDate("2025-01-01");
		while(start.isBefore(fin)) {
			deliveryDates.add(MonthUIAdapter.of(start));
			start=start.plusMonths(1);
		}
		DeliveryOrdersControl control = new DeliveryOrdersControl(DeliveryScheduleEnum.YEAR);
		List<Period> intervals = new ArrayList<Period>();
		intervals = control.calcDeliveryPeriods(deliveryDates, 0, intervals);
		System.out.println(intervals);
	}

}
