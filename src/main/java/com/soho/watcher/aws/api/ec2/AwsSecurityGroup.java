package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupEgressRequest;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.RevokeSecurityGroupEgressRequest;
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.soho.watcher.aws.api.AwsException;


public class AwsSecurityGroup {

    private static final Logger logger = LoggerFactory.getLogger(AwsSecurityGroup.class);

    public AwsSecurityGroup(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;


    //  function
    public String createSecurityGroup(CreateSecurityGroupRequest request, Map<String, String> tags) {
        // return groupId
        try {
            CreateSecurityGroupResult result = ec2.createSecurityGroup(request);
            // set tags
            if (result != null) {
                String groupId = result.getGroupId();

                tagHandler.setTags(groupId, tags);
                return groupId;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteSecurityGroup(String groupId) {
        boolean rtn = false;
        try {
            DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest();

            request.setGroupId(groupId);

            ec2.deleteSecurityGroup(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<SecurityGroup> listSecurityGroups() {

        List<SecurityGroup> lists = new ArrayList<SecurityGroup>();

        try {
            DescribeSecurityGroupsResult result = ec2.describeSecurityGroups();

            if (null != result) {
                lists = result.getSecurityGroups();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<SecurityGroup> listSecurityGroups(DescribeSecurityGroupsRequest request) {

        List<SecurityGroup> lists = new ArrayList<SecurityGroup>();
        try {
            DescribeSecurityGroupsResult result = ec2.describeSecurityGroups(request);

            if (null != result) {
                lists = result.getSecurityGroups();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<SecurityGroup> listSecurityGroupsByInstanceId(String instanceId) {

        List<SecurityGroup> lists = new ArrayList<SecurityGroup>();
        try {
            DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("attachment.instance-id").withValues(instanceId));
            request.withFilters(filters);
            return listSecurityGroups(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<SecurityGroup> listSecurityGroupsGroupByVpcId(String vpcId) {

        List<SecurityGroup> lists = new ArrayList<SecurityGroup>();
        try {
            DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("vpc-id").withValues(vpcId));
            request.withFilters(filters);

            return listSecurityGroups(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<SecurityGroup> listSecurityGroupsGroupBySubnetId(String subnetId) {

        List<SecurityGroup> lists = new ArrayList<SecurityGroup>();
        try {
            DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("subnet-id").withValues(subnetId));
            request.withFilters(filters);

            return listSecurityGroups(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public SecurityGroup selectSecurityGroup(String groupId) {
        try {
            DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();
            if (null != groupId) {
                request.withGroupIds(groupId);
                DescribeSecurityGroupsResult result = ec2.describeSecurityGroups(request);

                if (null != result) {
                    if (result.getSecurityGroups().size() > 0) {
                        return result.getSecurityGroups().get(0);
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


    public boolean authorizeSecurityGroupEgress(AuthorizeSecurityGroupEgressRequest request) {
        boolean rtn = false;
        try {
            //AuthorizeSecurityGroupEgressRequest request = new AuthorizeSecurityGroupEgressRequest();
            ec2.authorizeSecurityGroupEgress(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean authorizeSecurityGroupIngress(AuthorizeSecurityGroupIngressRequest request) {
        boolean rtn = false;
        try {
            //AuthorizeSecurityGroupIngressRequest request = new AuthorizeSecurityGroupIngressRequest();
            ec2.authorizeSecurityGroupIngress(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean revokeSecurityGroupEgress(RevokeSecurityGroupEgressRequest request) {
        boolean rtn = false;
        try {
            //RevokeSecurityGroupEgressRequest request = new RevokeSecurityGroupEgressRequest();
            ec2.revokeSecurityGroupEgress(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean revokeSecurityGroupIngress(RevokeSecurityGroupIngressRequest request) {
        boolean rtn = false;
        try {
            //RevokeSecurityGroupIngressRequest request = new RevokeSecurityGroupIngressRequest();
            ec2.revokeSecurityGroupIngress(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}