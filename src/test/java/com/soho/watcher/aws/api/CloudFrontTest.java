package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.model.DistributionSummary;
import com.amazonaws.services.cloudfront.model.ListDistributionsRequest;
import com.soho.watcher.aws.api.network.AwsCloudFront;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudFrontTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(CloudFrontTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsCloudFront cf;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonCloudFront client = factory.cloudFrontClient();
		this.cf = new AwsCloudFront(client);

		
		// list
		lists();

	}


	private void lists() {

		List<DistributionSummary> lists = cf.listDistributions(new ListDistributionsRequest());
		/*for(DistributionSummary ds: lists) {
		
		}*/
		System.out.println("AWS Cloud Front List : " + lists.toString());

	}

	

}
