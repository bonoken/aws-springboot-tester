package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.amazonaws.services.lambda.model.ListFunctionsRequest;
import com.soho.watcher.aws.api.serverless.AwsLambda;



@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(LambdaTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsLambda lambda;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AWSLambda client = factory.lambdaClient();
		this.lambda = new AwsLambda(client);


		System.out.println("lambda : " + lambda.listFunctions().toString());

		
/*		GetFunctionRequest request = new GetFunctionRequest();
		request.setFunctionName("kei-bot");
		// list
		System.out.println("lambda : " + lambda.getFunction(request).toString());*/
	
	}
	
	

}
