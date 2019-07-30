package com.soho.watcher.aws.api.mq;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.sqs.AmazonSQS;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SQSTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(SQSTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsSQS sqs;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonSQS awsSqs = factory.sqsClient();
		this.sqs = new AwsSQS(awsSqs);


		//
		listQueue();

	}
	
	private void listQueue() {
		
		List<String> list = sqs.listQueues();

		System.out.println("functions : " + list);

		for (String url : list) {
			String url_name = url.substring(url.lastIndexOf("/")+1, url.length());
			System.out.println("url_name : " + url_name);
		}
		
	}
	



}
