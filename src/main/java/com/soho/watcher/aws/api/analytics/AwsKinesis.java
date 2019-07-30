package com.soho.watcher.aws.api.analytics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.ListStreamsResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsKinesis {

    private static final Logger logger = LoggerFactory.getLogger(AwsKinesis.class);

    public AwsKinesis(AmazonKinesis client) {
        this.client = client;
    }

    private AmazonKinesis client;


    //  function
    public List<String> listStreams() {

        return listStreams(new DescribeStreamRequest());
    }

    public List<String> listStreams(DescribeStreamRequest request) {

        List<String> lists = new ArrayList<String>();

        try {

            ListStreamsResult result = client.listStreams();

            if (null != result) {
                lists = result.getStreamNames();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}