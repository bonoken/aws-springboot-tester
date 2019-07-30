package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Stack;
import com.soho.watcher.aws.api.AwsException;


public class AwsCloudFormation {

    private static final Logger logger = LoggerFactory.getLogger(AwsCloudFormation.class);

    public AwsCloudFormation(AmazonCloudFormation client) {
        this.client = client;
    }

    private AmazonCloudFormation client;

    //  function
    public List<Stack> listStacks() {
        List<Stack> lists = new ArrayList<Stack>();
        try {
            DescribeStacksResult result = client.describeStacks();

            if (null != result) {
                lists = result.getStacks();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
