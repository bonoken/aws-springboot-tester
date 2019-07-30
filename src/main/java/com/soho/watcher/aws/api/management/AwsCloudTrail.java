package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudtrail.AWSCloudTrail;
import com.amazonaws.services.cloudtrail.model.DescribeTrailsResult;
import com.amazonaws.services.cloudtrail.model.Trail;
import com.soho.watcher.aws.api.AwsException;


public class AwsCloudTrail {

    private static final Logger logger = LoggerFactory.getLogger(AwsCloudTrail.class);

    public AwsCloudTrail(AWSCloudTrail client) {
        this.client = client;
    }

    private AWSCloudTrail client;

    //  function
    public List<Trail> listTrails() {
        List<Trail> lists = new ArrayList<Trail>();
        try {
            DescribeTrailsResult result = client.describeTrails();

            if (null != result) {
                lists = result.getTrailList();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
