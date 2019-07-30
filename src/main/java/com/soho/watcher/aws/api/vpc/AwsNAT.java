package com.soho.watcher.aws.api.vpc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeNatGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeNatGatewaysResult;
import com.amazonaws.services.ec2.model.NatGateway;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsNAT {

    private static final Logger logger = LoggerFactory.getLogger(AwsNAT.class);

    public AwsNAT(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;


    //  function
    public List<NatGateway> listeNatGateways() {
        return listeNatGateways(new DescribeNatGatewaysRequest());
    }

    public List<NatGateway> listeNatGateways(DescribeNatGatewaysRequest request) {

        List<NatGateway> lists = new ArrayList<NatGateway>();
        try {

            DescribeNatGatewaysResult result = ec2.describeNatGateways(request);


            if (null != result) {
                lists = result.getNatGateways();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
