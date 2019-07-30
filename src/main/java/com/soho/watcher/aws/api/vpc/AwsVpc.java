package com.soho.watcher.aws.api.vpc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateVpcRequest;
import com.amazonaws.services.ec2.model.CreateVpcResult;
import com.amazonaws.services.ec2.model.DeleteVpcRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.ModifyVpcAttributeRequest;
import com.amazonaws.services.ec2.model.Vpc;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsVpc {

    private static final Logger logger = LoggerFactory.getLogger(AwsVpc.class);

    public AwsVpc(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;


    //  function
    public Vpc createVpc(CreateVpcRequest request, Map<String, String> tags) {
        try {

            //CreateVpcRequest request = new CreateVpcRequest();

            CreateVpcResult result = ec2.createVpc(request);

            if (result != null) {
                Vpc vpc = result.getVpc();
                // set tags
                tagHandler.setTags(vpc.getVpcId(), tags);
                return vpc;
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteVpc(String vpcId) {
        boolean rtn = false;
        try {

            DeleteVpcRequest request = new DeleteVpcRequest();
            request.setVpcId(vpcId);
            ec2.deleteVpc(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean modifyVpcAttribute(ModifyVpcAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            //DeleteVpcRequest request = new DeleteVpcRequest();
            ec2.modifyVpcAttribute(request);
            // set tags
            tagHandler.setTags(request.getVpcId(), tags);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<Vpc> listeVpcs() {
        List<Vpc> lists = new ArrayList<Vpc>();
        try {

            //DescribeVpcsRequest request = new DescribeVpcsRequest();
            DescribeVpcsResult result = ec2.describeVpcs();

            if (null != result) {
                lists = result.getVpcs();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Vpc> listeVpcs(DescribeVpcsRequest request) {
        List<Vpc> lists = new ArrayList<Vpc>();
        try {

            DescribeVpcsResult result = ec2.describeVpcs(request);


            if (null != result) {
                lists = result.getVpcs();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public Vpc selecteVpc(String vpcId) {
        try {

            DescribeVpcsRequest request = new DescribeVpcsRequest();

            if (null != vpcId) {
                Collection<String> vpcIds = new Vector<String>();
                vpcIds.add(vpcId);
                request.setVpcIds(vpcIds);
                DescribeVpcsResult result = ec2.describeVpcs(request);

                if (null != result) {
                    if (result.getVpcs().size() > 0) {
                        return result.getVpcs().get(0);
                    }
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

}
