package com.soho.watcher.aws.api;

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
import com.amazonaws.services.ec2.model.Reservation;
import com.soho.watcher.aws.api.ec2.AwsInstance;



@RunWith(SpringRunner.class)
@SpringBootTest
public class EC2Test {

	/** log */
	private static final Logger logger = LogManager.getLogger(EC2Test.class);

	@Autowired
	private AwsConfiguration config;

	private AwsInstance instance;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonEC2 ec2 = factory.ec2Client();
		this.instance = new AwsInstance(ec2);

		
		// list
		listInstance();

		searchInstance("kei-ssh");
	}


	private void listInstance() {

		List<Reservation> reservations = instance.listInstances();

		for(Reservation reseration : reservations) {
			System.out.println("all instance : " + reseration.getInstances().toString());
		}

	}

	private void searchInstance(String name) {
		// tag명 대소구분함
		DescribeInstancesRequest request = new DescribeInstancesRequest();

		List<Filter> filters = new Vector<Filter>();
		filters.add(new Filter("tag:Name").withValues(name));

		request.setFilters(filters);

		List<Reservation> reservations = instance.listInstances(request);

		for(Reservation reseration : reservations) {
			System.out.println("found instance : " + reseration.getInstances().toString());
		}

	}




}
