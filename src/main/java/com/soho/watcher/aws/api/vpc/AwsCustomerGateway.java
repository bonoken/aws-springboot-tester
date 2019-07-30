package com.soho.watcher.aws.api.vpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateCustomerGatewayRequest;
import com.amazonaws.services.ec2.model.CreateCustomerGatewayResult;
import com.amazonaws.services.ec2.model.CustomerGateway;
import com.amazonaws.services.ec2.model.DeleteCustomerGatewayRequest;
import com.amazonaws.services.ec2.model.DescribeCustomerGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeCustomerGatewaysResult;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsCustomerGateway {

    private static final Logger logger = LoggerFactory.getLogger(AwsCustomerGateway.class);

    public AwsCustomerGateway(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;


    //  function
    public CustomerGateway createCustomerGateway(CreateCustomerGatewayRequest request, Map<String, String> tags) {
        try {
            CreateCustomerGatewayResult result = ec2.createCustomerGateway(request);

            if (result != null) {
                CustomerGateway customerGateway = result.getCustomerGateway();
                tagHandler.setTags(customerGateway.getCustomerGatewayId(), tags);
                return customerGateway;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteCustomerGateway(String customerGatewayId) {
        boolean rtn = false;
        try {

            DeleteCustomerGatewayRequest request = new DeleteCustomerGatewayRequest();
            request.setCustomerGatewayId(customerGatewayId);
            ec2.deleteCustomerGateway(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<CustomerGateway> listCustomerGateways() {
        List<CustomerGateway> lists = new ArrayList<CustomerGateway>();
        try {
            //DescribeCustomerGatewaysRequest request = new DescribeCustomerGatewaysRequest();
            DescribeCustomerGatewaysResult result = ec2.describeCustomerGateways();

            if (null != result) {
                lists = result.getCustomerGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<CustomerGateway> listCustomerGateways(DescribeCustomerGatewaysRequest request) {

        List<CustomerGateway> lists = new ArrayList<CustomerGateway>();
        try {
            DescribeCustomerGatewaysResult result = ec2.describeCustomerGateways(request);

            if (null != result) {
                lists = result.getCustomerGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
