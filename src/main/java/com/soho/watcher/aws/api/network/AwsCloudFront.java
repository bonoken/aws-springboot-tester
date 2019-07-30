package com.soho.watcher.aws.api.network;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.model.DistributionSummary;
import com.amazonaws.services.cloudfront.model.ListDistributionsRequest;
import com.amazonaws.services.cloudfront.model.ListDistributionsResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsCloudFront {

    private static final Logger logger = LoggerFactory.getLogger(AwsCloudFront.class);

    public AwsCloudFront(AmazonCloudFront cf) {
        this.cf = cf;
    }

    private AmazonCloudFront cf;


    //  function
    public List<DistributionSummary> listDistributions(ListDistributionsRequest request) {
        List<DistributionSummary> lists = new ArrayList<DistributionSummary>();
        try {
            ListDistributionsResult result = cf.listDistributions(request);


            if (null != result) {
                if (result.getDistributionList().getItems().size() > 0) {
                    lists = result.getDistributionList().getItems();
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}