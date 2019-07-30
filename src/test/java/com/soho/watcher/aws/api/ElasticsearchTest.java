package com.soho.watcher.aws.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainRequest;
import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainResult;
import com.soho.watcher.aws.api.analytics.AwsElasticsearch;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(ElasticsearchTest.class);

	@Autowired
	private AwsConfiguration config;

	private AwsElasticsearch es;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		com.amazonaws.services.elasticsearch.AWSElasticsearch awsEs = factory.elasticsearchClient();
		this.es = new AwsElasticsearch(awsEs);
		
/*		DescribeElasticsearchDomainRequest request = new DescribeElasticsearchDomainRequest();
		request.setDomainName("ent-test");
		DescribeElasticsearchDomainResult result = awsEs.describeElasticsearchDomain(request);
		if(null != result) {
			String arn = result.getDomainStatus().getARN();
			System.out.println(result.getDomainStatus().getARN());
			if(null != arn && !"".equals(arn)) {
				String[] _arn = arn.split(":");
				System.out.println(_arn[4]);
				
			}
		}*/

		list();

	}

	private void list() {
		
		System.out.println("ES : " + es.listDomainNames());

	}



}
