package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.ListJobsRequest;
import com.amazonaws.services.iot.model.ListJobsResult;
import com.amazonaws.services.iot.model.ListTopicRulesRequest;
import com.amazonaws.services.iot.model.ListTopicRulesResult;



@RunWith(SpringRunner.class)
@SpringBootTest
public class IOTTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(IOTTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	//private AwsECS ecs;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AWSIot client = factory.iotClient();
		
		//listTopicRules(client);
		
		ListJobsResult result = client.listJobs(new ListJobsRequest());
		if(null != result) {
			System.out.println("iot job : "+result.getJobs());
		}
	}
	
	public void listTopicRules(AWSIot client) {
		ListTopicRulesResult result1 = client.listTopicRules(new ListTopicRulesRequest());
		if(null != result1) {
			System.out.println("iot rule : "+result1.getRules());
		}
	}
	

}