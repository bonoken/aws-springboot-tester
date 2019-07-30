package com.soho.watcher.aws.api.mq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsSQS {

    private static final Logger logger = LoggerFactory.getLogger(AwsSQS.class);

    public static final DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public AwsSQS(AmazonSQS sqs) {
        this.sqs = sqs;
    }

    private AmazonSQS sqs;


    // function
    public List<String> listQueues() {
        List<String> lists = new ArrayList<String>();
        try {
            ListQueuesResult result = sqs.listQueues();

            if (null != result) {
                lists = result.getQueueUrls();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<String> listQueues(ListQueuesRequest request) {
        List<String> lists = new ArrayList<String>();
        try {
            ListQueuesResult result = sqs.listQueues(request);

            if (null != result) {
                lists = result.getQueueUrls();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
