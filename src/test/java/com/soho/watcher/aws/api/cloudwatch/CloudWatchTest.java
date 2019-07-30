package com.soho.watcher.aws.api.cloudwatch;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soho.comm.json.JsonToXMLConverter;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;
import com.soho.watcher.aws.api.management.AwsCloudWatch;


@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan
public class CloudWatchTest {

	/** log */
	private static final Logger logger = LogManager.getLogger(CloudWatchTest.class);

	private ObjectMapper mapper = new ObjectMapper();
	private JsonToXMLConverter jsonConverter = new JsonToXMLConverter();

	@Autowired
	private AwsConfiguration config;

	private AwsCloudWatch mon;

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonCloudWatch cw = factory.cloudWatchClient("ap-northeast-2");
		
		//cw.listMetrics();
		System.out.println("list Metric : " + cw.listMetrics());
		//this.mon = new AwsCloudWatch(cw);
		
		//namespace
		//String namespace = null;
		//namespace = "AWS/SQS";
		//namespace = "AWS/ELB";
		//namespace = "AWS/ApplicationELB";
		//namespace = "AWS/NetworkELB";
		//namespace = "AWS/RDS";
		//namespace = "AWS/DynamoDB";
		//namespace = "AWS/Lambda";
		
		//List<Metric> lists = listMetric(namespace);

		//convertXML(lists);

		
		// ALL Metric
		//System.out.println("list Metric : " + mon.listMetrics());
	}

	
	private List<Metric> listMetric(String namespace){
		// Metric
		ListMetricsRequest req = new ListMetricsRequest();
		//req.setNamespace(namespace);

/*		DimensionFilter dimenctionFilter = new DimensionFilter();
		Collection<DimensionFilter> dimensions = new ArrayList<DimensionFilter>();
		dimensions.add(dimenctionFilter);
		req.setDimensions(dimensions);*/

		System.out.println("list Metric : " + mon.listMetrics());
		return mon.listMetrics(req);
	}
	
	
	private void convertXML(List<Metric> lists){
		for(int i=0; i <lists.size(); i++ ) {
			System.out.println(jsonConverter.convertToXml(lists.get(i)));			
		}
		
	}

}
