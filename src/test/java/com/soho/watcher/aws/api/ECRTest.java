package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.model.DescribeRepositoriesRequest;
import com.soho.watcher.aws.api.container.AwsECR;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ECRTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ECRTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsECR ecr;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonECR client = factory.ecrClient();
		this.ecr = new AwsECR(client);
		

		
		
		System.out.println("repository list : " + ecr.listRepositories());
		
	
		

	}
	
	
	
	
	

	

}
