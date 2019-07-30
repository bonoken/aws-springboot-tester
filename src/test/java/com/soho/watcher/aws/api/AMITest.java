package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.pricing.AWSPricing;
import com.soho.watcher.aws.api.ec2.AwsImage;




@RunWith(SpringRunner.class)
@SpringBootTest
public class AMITest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(AMITest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsImage ami;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonEC2 ec2 = factory.ec2Client();
		
		this.ami = new AwsImage(ec2);

		
		System.out.println("ami list : " + ami.listImagesInSelf());

	}
	
	
	
	
	

	

}
