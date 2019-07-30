package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.elasticache.AmazonElastiCache;
import com.soho.watcher.aws.api.db.AwsElastiCache;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ElastiCacheTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(ElastiCacheTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsElastiCache ec;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonElastiCache ec2 = factory.elastiCacheClient();
		this.ec = new AwsElastiCache(ec2);



		listCacheCluster();

	}

	private void listCacheCluster() {

		System.out.println("EC : " + ec.listCacheClusters());

	}

}
