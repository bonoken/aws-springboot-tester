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
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.waf.AWSWAF;
import com.soho.watcher.aws.api.container.AwsECS;



@RunWith(SpringRunner.class)
@SpringBootTest
public class SESTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(SESTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	//private AwsECS ecs;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonSimpleEmailService client = factory.sesClient();
		
		
//		com.amazonaws.services.elasticmapreduce.model.ListClustersResult result = client.list
//		if(null != result) {
//			System.out.println(result.getClusters());
//			for(ClusterSummary cluster : result.getClusters()) {
//				System.out.println(cluster.getName());
//			}
//		}
		
		
		
		//System.out.println("cluster list : " + client.listClusters());
		
		
	
		

	}
	
	
	
	
	

	

}
