package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.cloudfront.model.DistributionSummary;
import com.amazonaws.services.cloudfront.model.ListDistributionsRequest;
import com.amazonaws.services.cloudtrail.AWSCloudTrail;
import com.amazonaws.services.cloudtrail.model.Trail;
import com.soho.watcher.aws.api.management.AwsCloudTrail;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudTrailTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(CloudTrailTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsCloudTrail ct;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AWSCloudTrail client = factory.cloudTrailClient();
		this.ct = new AwsCloudTrail(client);

		
		// list
		lists();

	}


	private void lists() {

		List<Trail> lists = ct.listTrails();

		System.out.println("AWS Cloud Trail List : " + lists.toString());

	}

	

}
