package com.soho.watcher.aws.api.cloudwatch.vpc;

import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.NatGateway;
import com.amazonaws.services.ec2.model.Reservation;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;
import com.soho.watcher.aws.api.ec2.AwsInstance;
import com.soho.watcher.aws.api.vpc.AwsNAT;



@RunWith(SpringRunner.class)
@SpringBootTest
public class VpcTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(VpcTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsNAT nat;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonEC2 ec2 = factory.ec2Client();
		this.nat = new AwsNAT(ec2);
		
		// NAT
		listNATGateway();
		
	}


	private void listNATGateway() {

		List<NatGateway> lists = nat.listeNatGateways();
		System.out.println("NAT Gateway List : " + lists);
		

	}






}
