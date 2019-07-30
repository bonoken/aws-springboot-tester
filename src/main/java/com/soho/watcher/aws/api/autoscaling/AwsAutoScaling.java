package com.soho.watcher.aws.api.autoscaling;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.model.AutoScalingGroup;
import com.amazonaws.services.autoscaling.model.AutoScalingInstanceDetails;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsRequest;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingGroupsResult;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingInstancesRequest;
import com.amazonaws.services.autoscaling.model.DescribeAutoScalingInstancesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsAutoScaling {

    private static final Logger logger = LoggerFactory.getLogger(AwsAutoScaling.class);

    public AwsAutoScaling(AmazonAutoScaling client) {
        this.client = client;
    }

    private AmazonAutoScaling client;


    //  function
    public List<AutoScalingGroup> listAutoScalingGroups() {

        List<AutoScalingGroup> lists = new ArrayList<AutoScalingGroup>();

        try {
            DescribeAutoScalingGroupsResult result = client.describeAutoScalingGroups();
            if (null != result) {
                lists = result.getAutoScalingGroups();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<AutoScalingGroup> listAutoScalingGroups(DescribeAutoScalingGroupsRequest request) {

        List<AutoScalingGroup> lists = new ArrayList<AutoScalingGroup>();
        try {
            DescribeAutoScalingGroupsResult result = client.describeAutoScalingGroups(request);
            if (null != result) {
                lists = result.getAutoScalingGroups();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<AutoScalingInstanceDetails> listAutoScalingInstances() {

        List<AutoScalingInstanceDetails> lists = new ArrayList<AutoScalingInstanceDetails>();
        try {
            DescribeAutoScalingInstancesResult result = client.describeAutoScalingInstances();
            if (null != result) {
                lists = result.getAutoScalingInstances();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<AutoScalingInstanceDetails> listAutoScalingInstances(DescribeAutoScalingInstancesRequest request) {

        List<AutoScalingInstanceDetails> lists = new ArrayList<AutoScalingInstanceDetails>();
        try {
            DescribeAutoScalingInstancesResult result = client.describeAutoScalingInstances(request);
            if (null != result) {
                lists = result.getAutoScalingInstances();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}