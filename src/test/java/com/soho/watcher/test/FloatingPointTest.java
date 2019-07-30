package com.soho.watcher.test;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class FloatingPointTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(FloatingPointTest.class);
	

	@Test
	public void contextLoads()  {
		//3.221225472E9             , val 2.0415066112E10
		//threshold : 3.221225472E9 , val 2.0415066112E10
		double t = 3.221225472E9;
		double v = 2.0415066112E10;
		double g =  3*( 1024 * 1024 *1024);
		double m = 3 * Math.pow(1024, 3);
		//System.out.println("t "+ Integer.toHexString(Float.floatToIntBits((float) t)).toUpperCase());
		//System.out.println("v "+ Integer.toHexString(Float.floatToIntBits((float) v)).toUpperCase());
		System.out.println("g " + g);
		System.out.println("m " + m);
		BigDecimal dt = new BigDecimal(t);
		System.out.println(" t " + dt.toString());
		BigDecimal dv = new BigDecimal(v);
		System.out.println(" v " + dv.toString());


		System.out.println(m/1024);
		System.out.println(m/(1024*1024));
		System.out.println(m/(1024*1024*1024));

		System.out.println(v/1024);
		System.out.println(v/(1024*1024));
		System.out.println(v/(1024*1024*1024));

		System.out.println(Double.compare(g, v));
		System.out.println(Double.compare(m, v));
	}
	
	
	
	

}
