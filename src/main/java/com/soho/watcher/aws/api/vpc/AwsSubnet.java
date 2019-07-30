package com.soho.watcher.aws.api.vpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateSubnetRequest;
import com.amazonaws.services.ec2.model.CreateSubnetResult;
import com.amazonaws.services.ec2.model.DeleteSubnetRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.ModifySubnetAttributeRequest;
import com.amazonaws.services.ec2.model.Subnet;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;

public class AwsSubnet {

    private static final Logger logger = LoggerFactory.getLogger(AwsSubnet.class);

    public AwsSubnet(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public Subnet createSubnet(CreateSubnetRequest request, Map<String, String> tags) {
        try {
            //CreateSubnetRequest request = new CreateSubnetRequest();
            CreateSubnetResult result = ec2.createSubnet(request);
            if (result != null) {
                Subnet subnet = result.getSubnet();
                // set tags
                tagHandler.setTags(subnet.getSubnetId(), tags);
                return subnet;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteSubnet(String subnetId) {
        boolean rtn = false;
        try {

            DeleteSubnetRequest request = new DeleteSubnetRequest();
            request.setSubnetId(subnetId);
            ec2.deleteSubnet(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<Subnet> listSubnets() {
        List<Subnet> lists = new ArrayList<Subnet>();
        try {
            //DescribeSubnetsRequest request = new DescribeSubnetsRequest();
            DescribeSubnetsResult result = ec2.describeSubnets();
            if (null != result) {
                lists = result.getSubnets();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<Subnet> listSubnets(DescribeSubnetsRequest request) {
        List<Subnet> lists = new ArrayList<Subnet>();
        try {
            DescribeSubnetsResult result = ec2.describeSubnets(request);
            if (null != result) {
                lists = result.getSubnets();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Subnet> listSubnetsByVpcId(String vpcId) {
        List<Subnet> lists = new ArrayList<Subnet>();
        try {

            DescribeSubnetsRequest request = new DescribeSubnetsRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("vpc-id").withValues(vpcId));
            request.withFilters(filters);

            return this.listSubnets(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public boolean modifySubnetAttribute(ModifySubnetAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            //ModifySubnetAttributeRequest request = new ModifySubnetAttributeRequest();
            ec2.modifySubnetAttribute(request);
            // set tags
            tagHandler.setTags(request.getSubnetId(), tags);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}