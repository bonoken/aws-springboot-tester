package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.model.ListClustersResult;
import com.soho.watcher.aws.api.container.AwsECS;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ECSTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ECSTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsECS ecs;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonECS client = factory.ecsClient();
		this.ecs = new AwsECS(client);
		
		List<String> lists = ecs.listClusters();
		
		for(String arn : lists) {
				String[] _arn = arn.split("/");
				System.out.println(_arn[1]);
		}
		
		
		System.out.println("cluster list : " + client.listClusters());
		
		
	
		

	}
	
	
	
	
	

	

}
