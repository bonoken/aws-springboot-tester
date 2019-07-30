package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.soho.watcher.aws.api.db.AwsRDS;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RDSTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(RDSTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsRDS rds;
	
	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonRDS awsRds = factory.rdsClient();
		this.rds = new AwsRDS(awsRds);
		
		
		// list
		DescribeDBInstancesRequest request = new DescribeDBInstancesRequest();
		//request.setDBInstanceIdentifier("seoul-prd-bespinam-mariadb01");
		
		List<DBInstance> rdsInstances = rds.listDBInstances();
		System.out.println("rds instance : " + rdsInstances);
	}
	
	

}
