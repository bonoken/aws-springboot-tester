package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.sqs.AmazonSQS;
import com.soho.watcher.aws.api.mq.AwsSQS;



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
		AmazonSQS client = factory.sqsClient();
		this.sqs = new AwsSQS(client);

		
		// list
		lists();

	}


	private void lists() {

		List<String> lists = sqs.listQueues();

		System.out.println("AWS SQS List : " + lists.toString());

	}

	

}
