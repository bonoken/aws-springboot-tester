package com.soho.watcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soho.watcher.aws.api.AwsConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	
	@Autowired
	private AwsConfiguration config;
	
	@Test
	public void contextLoads() {
	}

}
