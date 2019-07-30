package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.redshift.AmazonRedshift;
import com.soho.watcher.aws.api.db.AwsRedShift;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RedshiftTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(RedshiftTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsRedShift rs;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonRedshift client = factory.redshiftClient();
		this.rs = new AwsRedShift(client);



		lists();

	}

	private void lists() {

		System.out.println("redshift list : " + rs.listClusters());

	}

}
