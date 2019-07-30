package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;



@RunWith(SpringRunner.class)
@SpringBootTest
public class EMRTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(EMRTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	//private AwsECS ecs;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonElasticMapReduce client = factory.elasticMapReduceClient();
		
		
		com.amazonaws.services.elasticmapreduce.model.ListClustersResult result = client.listClusters();
		if(null != result) {
			System.out.println(result.getClusters());
//			for(ClusterSummary cluster : result.getClusters()) {
//				System.out.println(cluster.getName());
//			}
		}
		
		
		//System.out.println("cluster list : " + client.listClusters());
	}
	
	
	

}