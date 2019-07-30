package com.soho.watcher.aws.api.serverless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;
import com.soho.watcher.aws.api.network.AwsApiGateway;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiGatewayTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ApiGatewayTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsApiGateway gw;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonApiGateway awsRds = factory.apiGatewayClient();
		
		this.gw = new AwsApiGateway(awsRds);
		

		listRestApi();
		
	}
	
	private void listRestApi() {

		System.out.println("functions : " + gw.listRestApis());
		
	}
	
	

}
