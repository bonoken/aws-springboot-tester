package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.soho.watcher.aws.api.management.AwsIAM;



@RunWith(SpringRunner.class)
@SpringBootTest
public class IAMTest {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(IAMTest.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsIAM iam;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonIdentityManagement awsIam = factory.iamClient();
		this.iam = new AwsIAM(awsIam);
		

		
		System.out.println("gropu list : " + iam.listGroups());
		System.out.println("user list : " + iam.listUsers());
		System.out.println("AccessKey list : " +iam.listAccessKeys());
		
		System.out.println("role list : " + iam.listRoles());
		System.out.println("profile : " + iam.listInstanceProfiles());
	}
	
	
	
	
	

	

}
