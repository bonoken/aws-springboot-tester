package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.soho.watcher.aws.api.autoscaling.AwsAutoScaling;



@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoScalingTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(AutoScalingTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsAutoScaling as;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonAutoScaling ec2 = factory.autoScalingClient();
		this.as = new AwsAutoScaling(ec2);



		lists();

	}

	private void lists() {

		System.out.println("AutoScaling list : " + as.listAutoScalingGroups());

	}

}
