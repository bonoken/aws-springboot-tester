package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.soho.watcher.aws.api.elb.AwsLoadBalancer;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ElbTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ElbTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsLoadBalancer elb;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonElasticLoadBalancing awsElb = factory.elbClient();
		this.elb = new AwsLoadBalancer(awsElb);
		

		// list
		System.out.println("elb : " + elb.listLoadBalancers());
	
	}
	
	

}
