package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesRequest;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsAvailabilityZone {

    private static final Logger logger = LoggerFactory.getLogger(AwsAvailabilityZone.class);


    public AwsAvailabilityZone(AmazonEC2 ec2) {
        this.ec2 = ec2;
    }

    private AmazonEC2 ec2;

    // function
    public List<AvailabilityZone> listAvailabilityZones(DescribeAvailabilityZonesRequest request) {

        List<AvailabilityZone> lists = new ArrayList<AvailabilityZone>();

        try {

            DescribeAvailabilityZonesResult result = ec2.describeAvailabilityZones(request);

            if (null != result) {
                lists = result.getAvailabilityZones();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<AvailabilityZone> listAvailabilityZones() {

        List<AvailabilityZone> lists = new ArrayList<AvailabilityZone>();

        try {

            //DescribeAvailabilityZonesRequest request = new DescribeAvailabilityZonesRequest();

            DescribeAvailabilityZonesResult result = ec2.describeAvailabilityZones();

            if (null != result) {
                lists = result.getAvailabilityZones();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}