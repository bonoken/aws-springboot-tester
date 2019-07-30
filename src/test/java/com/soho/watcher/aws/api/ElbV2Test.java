package com.soho.watcher.aws.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancingv2.model.DescribeLoadBalancersRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.LoadBalancer;
import com.soho.watcher.aws.api.elb.AwsLoadBalancerV2;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ElbV2Test {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(ElbV2Test.class);
	
	@Autowired
    private AwsConfiguration config;
	
	private AwsLoadBalancerV2 elb;
	
	@Test
	public void contextLoads()  {
		
		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonElasticLoadBalancing awsElb = factory.elbV2Client();
		this.elb = new AwsLoadBalancerV2(awsElb);

		
		// list
		logger.debug("elb : " + elb.listLoadBalancers().toString());
		
		// list
		DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
		List<LoadBalancer> lists = elb.listLoadBalancers(request);
		for(LoadBalancer lb : lists) {
			String arn = lb.getLoadBalancerArn();
		
			System.out.println(arn.substring(arn.indexOf("/")+1,arn.length()));
		}
		
		
	}
	
	

}
