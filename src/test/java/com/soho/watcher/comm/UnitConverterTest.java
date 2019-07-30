package com.soho.watcher.comm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soho.comm.unit.UnitConverter;
import com.soho.comm.unit.UnitConverter2;
import com.soho.comm.unit.UnitObject;




@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitConverterTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(UnitConverterTest.class);
	
	
	@Test
	public void contextLoads()  {

		//byteConversionTest();
		//byteConversionUnitTest();
		reverseByteConversionUnitTest();
	
	}
	
	private void byteConversionTest() {
		String[] sizes = { "1  kb", "100 ", "1   MiB", "1gb", "1.5J", "123344", "2424214551" ,"aggaag"};
		for (String size : sizes) {
			logger.debug(String.format("%s = %s bytes", size, String.valueOf((double)UnitConverter2.convertUnit(size))) );
		}
		
	}
	
	private void byteConversionUnitTest() {
		String[] sizes = { "1  kb", "100 ", "1   MiB", "1gb", "1.5J", "123344", "2424214551", "2424214551 kilo b", "323424 m k b", "300 GB" };
		for (String size : sizes) {
			logger.debug(String.format("%s = %s bytes", size, UnitConverter.convertUnit(size).toString()) );
		}
		
	}
	
	private void reverseByteConversionUnitTest() {
		
	
		//String[] sizes = { "1  kb", "100 ", "1   MiB", "1gb", "1.5J", "123344", "2424214551", "2424214551 kilo b", "323424 m k b", "aggaag" ,"2.0415066112E10 Gb"};
		
		
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(9.9509809152E10, "Gb"));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(123344, null));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(0.00, null));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(0.3425252525252, null));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(1024.2342, null));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(1024.2, "B"));
			logger.debug("output : " + UnitConverter.convertUnitStringForByte(1024.2, "KB"));
	}
	
	

}
