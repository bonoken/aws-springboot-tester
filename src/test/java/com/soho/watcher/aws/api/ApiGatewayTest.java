package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.soho.watcher.aws.api.network.AwsApiGateway;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiGatewayTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(ApiGatewayTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsApiGateway ag;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonApiGateway client = factory.apiGatewayClient();
		this.ag = new AwsApiGateway(client);



		lists();

	}

	private void lists() {

		System.out.println("api list : " + ag.listRestApis());

	}

}
