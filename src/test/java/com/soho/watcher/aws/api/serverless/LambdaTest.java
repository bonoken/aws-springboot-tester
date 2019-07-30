package com.soho.watcher.aws.api.serverless;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;



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
		AWSLambda awsLambda = factory.lambdaClient();
		this.lambda = new AwsLambda(awsLambda);

		//
		listFunction();

		//getFunction("");
	}


	private void listFunction() {

		// list
		List<FunctionConfiguration> functions = new ArrayList<FunctionConfiguration>();

		functions = lambda.listFunctions();
		System.out.println("functions : " + functions);
	}

	private void getFunction(String functionName) {

		GetFunctionRequest request = new GetFunctionRequest();
		request.setFunctionName(functionName);

		System.out.println("functions : " + lambda.getFunction(request));
	}



}
