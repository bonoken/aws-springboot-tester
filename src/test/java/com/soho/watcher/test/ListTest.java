package com.soho.watcher.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;




@RunWith(SpringRunner.class)
@SpringBootTest
public class ListTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(ListTest.class);


	@Test
	public void contextLoads() {

		List<String> regions = new ArrayList<String>();
		regions.add("ac-dddd-01");
		regions.add("ac-ppdaff");
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonStr = "";

		try {
			jsonStr = mapper.writeValueAsString(regions);
			try {
				List<String> rtn = mapper.readValue(jsonStr, new TypeReference<List<String>>() {});
				
				System.out.println(rtn.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
		
	}
	
	









}
