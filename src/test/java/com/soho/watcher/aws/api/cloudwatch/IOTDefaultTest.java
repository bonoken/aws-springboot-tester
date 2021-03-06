package com.soho.watcher.aws.api.cloudwatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soho.comm.date.DateTimeRange;
import com.soho.comm.json.JsonToXMLConverter;
import com.soho.watcher.aws.api.AwsClientFactory;
import com.soho.watcher.aws.api.AwsConfiguration;
import com.soho.watcher.aws.api.management.AwsCloudWatch;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IOTDefaultTest {
	
	//https://docs.aws.amazon.com/ko_kr/AmazonCloudWatch/latest/monitoring/iot-metricscollected.html
	
	/** log */
	private static final Logger logger = LogManager.getLogger(IOTDefaultTest.class);

	private ObjectMapper mapper = new ObjectMapper();
	private JsonToXMLConverter jsonConverter = new JsonToXMLConverter();

	@Autowired
	private AwsConfiguration config;

	private AwsCloudWatch mon;

	//
	private String namespace = "AWS/IoT";
	//private String dimensionName = "RuleName";

	@Test
	public void contextLoads()  {

		AwsClientFactory factory = new AwsClientFactory(config);
		AmazonCloudWatch cw = factory.cloudWatchClient("ap-northeast-2");
		this.mon = new AwsCloudWatch(cw);



		listMetric();

		getMetricStatistic();

	}

	public List<Metric> listMetric(){

		// Metric
		ListMetricsRequest req = new ListMetricsRequest();

		req.setNamespace(namespace);

		//DimensionFilter dimenctionFilter = new DimensionFilter();

		//Collection<DimensionFilter> dimensions = new ArrayList<DimensionFilter>();
		//dimensions.add(dimenctionFilter);

		//req.setDimensions(dimensions);

		logger.debug("list Metric : " + mon.listMetrics(req));

		return mon.listMetrics(req);
	}

	public void getMetricStatistic(){

		// Metric
		ListMetricsRequest reqMetric = new ListMetricsRequest();
		reqMetric.setNamespace(namespace);


		Date[] timeRangeDate = null;
		timeRangeDate = DateTimeRange.getLastMinutesRangeISO8601Dates(DateTimeRange.getIntervalSeconds(1,"min") / 60, true);

		logger.debug("start time : "+ timeRangeDate[0]);
		logger.debug("end time : " + timeRangeDate[1]);




			GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
			request.setNamespace(namespace);
			List<String> statistics = new ArrayList<String>();
			statistics.add("Average");
			statistics.add("Sum");
			statistics.add("Maximum");
			statistics.add("Minimum");
			request.setStatistics(statistics);
			request.setMetricName("RulesExecuted");
			//request.setMetricName("BlockedTransactions");
			request.setPeriod(DateTimeRange.getIntervalSeconds(1,"min"));
			request.setStartTime(timeRangeDate[0]);
			request.setEndTime(timeRangeDate[1]);

			List<Dimension> dimensions = new ArrayList<Dimension>();


			request.withDimensions(dimensions);
			//logger.debug(request.toString());
			for(Datapoint datum :   mon.getMetricStatistics(request)) {
				if(null != datum) {
					System.out.println("metric name : " + "RulesExecuted" + " , datum : " + datum.toString() );
				}
			}

	}

}
