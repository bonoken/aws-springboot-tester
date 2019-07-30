package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.AlarmHistoryItem;
import com.amazonaws.services.cloudwatch.model.DashboardEntry;
import com.amazonaws.services.cloudwatch.model.DashboardValidationMessage;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DeleteDashboardsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmHistoryRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmHistoryResult;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsForMetricRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsForMetricResult;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.cloudwatch.model.ListDashboardsRequest;
import com.amazonaws.services.cloudwatch.model.ListDashboardsResult;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.MetricAlarm;
import com.amazonaws.services.cloudwatch.model.PutDashboardRequest;
import com.amazonaws.services.cloudwatch.model.PutDashboardResult;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.soho.watcher.aws.api.AwsException;


public class AwsCloudWatch {

    private static final Logger logger = LoggerFactory.getLogger(AwsCloudWatch.class);

    public AwsCloudWatch(AmazonCloudWatch cloudWatch) {
        this.cloudWatch = cloudWatch;
    }

    private AmazonCloudWatch cloudWatch;

    //  function

    // Metric
    public List<Metric> listMetrics() {

        List<Metric> lists = new ArrayList<Metric>();
        try {
            ListMetricsResult result = cloudWatch.listMetrics();

            if (null != result) {
                lists = result.getMetrics();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<Metric> listMetrics(ListMetricsRequest request) {

        List<Metric> lists = new ArrayList<Metric>();
        try {
            ListMetricsResult result = cloudWatch.listMetrics(request);

            if (null != result) {
                lists = result.getMetrics();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    // Metric Alarm
    public boolean deleteAlarms(String alarmName) {
        boolean rtn = false;
        try {

            DeleteAlarmsRequest request = new DeleteAlarmsRequest();
            request.withAlarmNames(alarmName);

            //DeleteAlarmsResult result =  cloudWatch.deleteAlarms(request);
            cloudWatch.deleteAlarms(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean putMetricAlarm(PutMetricAlarmRequest request) {
        boolean rtn = false;
        try {

            //PutMetricAlarmRequest request = new PutMetricAlarmRequest();

            //PutMetricAlarmResult result = cloudWatch.putMetricAlarm(request);
            cloudWatch.putMetricAlarm(request);
            // WIP(work in process)

            // set tags
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<MetricAlarm> listAlarms(DescribeAlarmsRequest request) {
        List<MetricAlarm> lists = new ArrayList<MetricAlarm>();
        try {
            DescribeAlarmsResult result = cloudWatch.describeAlarms(request);

            if (null != result) {
                lists = result.getMetricAlarms();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<MetricAlarm> listAlarms() {

        List<MetricAlarm> lists = new ArrayList<MetricAlarm>();
        try {

            DescribeAlarmsResult result = cloudWatch.describeAlarms();

            if (null != result) {
                lists = result.getMetricAlarms();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public MetricAlarm selectAlarm(String alarmName) {
        try {

            DescribeAlarmsRequest request = new DescribeAlarmsRequest();


            if (null != alarmName) {
                request.withAlarmNames(alarmName);
				/*				
				Collection<String> alarmNames = new Vector<String>();
				alarmNames.add(alarmName);
				request.withAlarmNames(alarmNames);
				 */

                DescribeAlarmsResult result = cloudWatch.describeAlarms(request);

                if (null != result) {
                    if (result.getMetricAlarms().size() > 0) {
                        return result.getMetricAlarms().get(0);
                    }
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public List<MetricAlarm> listAlarmsForMetric(DescribeAlarmsForMetricRequest request) {
        List<MetricAlarm> lists = new ArrayList<MetricAlarm>();
        try {

            DescribeAlarmsForMetricResult result = cloudWatch.describeAlarmsForMetric(request);

            if (null != result) {
                lists = result.getMetricAlarms();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    // MetricData
    public boolean putMetricData(PutMetricDataRequest request) {
        boolean rtn = false;
        try {

            //PutMetricDataRequest request = new PutMetricDataRequest();

            cloudWatch.putMetricData(request);
            // WIP(work in process)
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    // Alram History
    public List<AlarmHistoryItem> listAlarmHistory(DescribeAlarmHistoryRequest request) {
        List<AlarmHistoryItem> lists = new ArrayList<AlarmHistoryItem>();
        try {

            DescribeAlarmHistoryResult result = cloudWatch.describeAlarmHistory(request);

            if (null != result) {
                lists = result.getAlarmHistoryItems();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<AlarmHistoryItem> listAlarmHistory() {
        List<AlarmHistoryItem> lists = new ArrayList<AlarmHistoryItem>();
        try {
            //DescribeAlarmHistoryRequest request = new DescribeAlarmHistoryRequest();
            DescribeAlarmHistoryResult result = cloudWatch.describeAlarmHistory();

            if (null != result) {
                lists = result.getAlarmHistoryItems();
            }

        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            logger.error("Error Message:    " + ase.getMessage());
            logger.error("HTTP Status Code: " + ase.getStatusCode());
            logger.error("AWS Error Code:   " + ase.getErrorCode());
            logger.error("Error Type:       " + ase.getErrorType());
            logger.error("Request ID:       " + ase.getRequestId());

        } catch (AmazonClientException ace) {
            logger.error("Error Message: " + ace.getMessage());
        }
        return lists;
    }

    // Metric (모듈)
    public List<Datapoint> getMetricStatistics(GetMetricStatisticsRequest request) {
        List<Datapoint> lists = new ArrayList<Datapoint>();
        try {
            GetMetricStatisticsResult result = cloudWatch.getMetricStatistics(request);

            if (null != result) {
                lists = result.getDatapoints();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Datapoint> getMetricStatistic(String namespace, String metricNm, List<Dimension> dimensions, List<String> statistics, Integer period, Date startTime, Date endTime) {
        List<Datapoint> lists = new ArrayList<Datapoint>();

        try {
            ListMetricsRequest reqMetric = new ListMetricsRequest();
            reqMetric.setNamespace(namespace);

            GetMetricStatisticsRequest request = new GetMetricStatisticsRequest();
            request.setNamespace(namespace);

            request.setStatistics(statistics);
            request.setMetricName(metricNm);

            request.setPeriod(period);
            request.setStartTime(startTime);
            request.setEndTime(endTime);

            request.withDimensions(dimensions);

            GetMetricStatisticsResult result = cloudWatch.getMetricStatistics(request);

            if (null != result) {
                lists = result.getDatapoints();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;

    }


    // DashBoard
    public List<DashboardValidationMessage> putDashboard(PutDashboardRequest request) {

        List<DashboardValidationMessage> validationMessages = new ArrayList<DashboardValidationMessage>();
        try {
            //PutDashboardRequest request = new PutDashboardRequest();

            PutDashboardResult result = cloudWatch.putDashboard(request);
            // WIP(work in process)
            if (null != result) {
                validationMessages = result.getDashboardValidationMessages();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return validationMessages;
    }

    public boolean deleteDashboards(String dashboardName) {

        try {

            DeleteDashboardsRequest request = new DeleteDashboardsRequest();
            request.withDashboardNames(dashboardName);

            cloudWatch.deleteDashboards(request);

            return true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }

    public List<DashboardEntry> listDashboards(ListDashboardsRequest request) {
        List<DashboardEntry> lists = new ArrayList<DashboardEntry>();
        try {

            ListDashboardsResult result = cloudWatch.listDashboards(request);


            if (null != result) {
                lists = result.getDashboardEntries();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<DashboardEntry> listDashboards() {
        List<DashboardEntry> lists = new ArrayList<DashboardEntry>();

        try {
            ListDashboardsRequest request = new ListDashboardsRequest();
            ListDashboardsResult result = cloudWatch.listDashboards(request);

            if (null != result) {
                lists = result.getDashboardEntries();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    // util
    public List<String> getFilteredMetricsForDimension(List<Metric> metricList, Dimension dimension) {
        List<String> rtnMetrics = new ArrayList<String>();
        for (Metric metric : metricList) {
            String metricNm = metric.getMetricName();
            if (!rtnMetrics.contains(metricNm)) {
                for (Dimension tDimension : metric.getDimensions()) {
                    // b'cos aurora cluster
                    if (tDimension.getValue().equals(dimension.getValue()) || tDimension.getValue().equals(dimension.getValue() + "-cluster")) {
                        //if(tDimension.getValue().contains(dimension.getValue())) {
                        rtnMetrics.add(metricNm);
                        //logger.debug(metricNm);
                        System.out.println(metricNm);
                    }
                }
            }
        }
        return rtnMetrics;
    }

    public Dimension createDimension(String name, String value) {
        Dimension dim = new Dimension();
        dim.setName(name);
        dim.setValue(value);
        return dim;
    }

    public List<Dimension> createDimensionList(String name, String value) {
        List<Dimension> dimensionList = new ArrayList<Dimension>();
        Dimension dim = new Dimension();
        dim.setName(name);
        dim.setValue(value);
        dimensionList.add(dim);
        return dimensionList;
    }

    public List<Dimension> createDimensionList(Dimension dimension) {
        List<Dimension> dimensionList = new ArrayList<Dimension>();
        dimensionList.add(dimension);
        return dimensionList;
    }

    public Double getBucketSizeBytes(AmazonCloudWatch cloudWatch, String bucketName, Date endTime) {
        Double rtn = (double) 0;
        try {
            Integer period = Integer.valueOf(108000);
            Date startTime = new Date(endTime.getTime() - (Integer.valueOf(108000).intValue() * 1000));
            List<String> statistics = new ArrayList<String>();
            statistics.add("Average");

            List<Dimension> dimensions = this.createDimensionList("BucketName", bucketName);
            Dimension d2 = createDimension("StorageType", "StandardStorage");
            dimensions.add(d2);

            List<Datapoint> result = this.getMetricStatistic("AWS/S3", "BucketSizeBytes", dimensions, statistics, period, startTime, endTime);
            if (null != result) {
                for (Datapoint datapoint : result) {
                    rtn = datapoint.getAverage();
                    //logger.debug("bucketName : " + bucketName + " ,BucketSizeBytes Data : " + datapoint.getAverage());
                }
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public Double getNumberOfObjects(String bucketName, Date endTime) {
        Double rtn = (double) 0;
        try {
            Integer period = Integer.valueOf(108000);
            Date startTime = new Date(endTime.getTime() - (Integer.valueOf(108000).intValue() * 1000));
            List<String> statistics = new ArrayList<String>();
            statistics.add("Average");
            List<Dimension> dimensions = this.createDimensionList("BucketName", bucketName);
            Dimension d2 = createDimension("StorageType", "AllStorageTypes");
            dimensions.add(d2);

            List<Datapoint> result = this.getMetricStatistic("AWS/S3", "NumberOfObjects", dimensions, statistics, period, startTime, endTime);
            if (null != result) {
                for (Datapoint datapoint : result) {
                    rtn = datapoint.getAverage();
                    //logger.debug("bucketName : " + bucketName + " ,NumberOfObjects Data : " + datapoint.getAverage());
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

}
