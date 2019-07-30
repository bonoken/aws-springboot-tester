package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.ec2.AmazonEC2;
import com.soho.watcher.aws.api.ec2.AwsElasticIp;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticIPTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ElasticIPTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsElasticIp eip;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonEC2 ec2 = factory.ec2Client();
		this.eip = new AwsElasticIp(ec2);
		

		
		
		System.out.println("eip list : " + eip.listAddresses());

	}
	
	
	
	
	

	

}
