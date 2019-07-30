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
import com.amazonaws.services.ec2.model.AttachNetworkInterfaceRequest;
import com.amazonaws.services.ec2.model.AttachNetworkInterfaceResult;
import com.amazonaws.services.ec2.model.CreateNetworkInterfaceRequest;
import com.amazonaws.services.ec2.model.CreateNetworkInterfaceResult;
import com.amazonaws.services.ec2.model.DeleteNetworkInterfaceRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.amazonaws.services.ec2.model.DetachNetworkInterfaceRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.ModifyNetworkInterfaceAttributeRequest;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.soho.watcher.aws.api.AwsException;


public class AwsNetworkInterface {

    private static final Logger logger = LoggerFactory.getLogger(AwsNetworkInterface.class);

    public AwsNetworkInterface(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public NetworkInterface createNetworkInterface(CreateNetworkInterfaceRequest request, Map<String, String> tags) {
        try {

            //CreateNetworkInterfaceRequest request = new CreateNetworkInterfaceRequest();

            CreateNetworkInterfaceResult result = ec2.createNetworkInterface(request);

            // set tags
            if (result != null) {
                NetworkInterface networkInterface = new NetworkInterface();
                networkInterface = result.getNetworkInterface();

                tagHandler.setTags(networkInterface.getNetworkInterfaceId(), tags);
                return networkInterface;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteNetworkInterface(String networkInterfaceId) {
        boolean rtn = false;
        try {

            DeleteNetworkInterfaceRequest request = new DeleteNetworkInterfaceRequest();

            request.setNetworkInterfaceId(networkInterfaceId);

            ec2.deleteNetworkInterface(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<NetworkInterface> listNetworkInterfaces() {
        List<NetworkInterface> lists = new ArrayList<NetworkInterface>();
        try {
            //DescribeNetworkInterfacesRequest request = new DescribeNetworkInterfacesRequest();
            DescribeNetworkInterfacesResult result = ec2.describeNetworkInterfaces();
            if (null != result) {
                lists = result.getNetworkInterfaces();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<NetworkInterface> listNetworkInterfaces(DescribeNetworkInterfacesRequest request) {

        List<NetworkInterface> lists = new ArrayList<NetworkInterface>();
        try {
            DescribeNetworkInterfacesResult result = ec2.describeNetworkInterfaces(request);
            if (null != result) {
                lists = result.getNetworkInterfaces();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<NetworkInterface> listNetworkInterfacesByVpcId(String vpcId) {
        List<NetworkInterface> lists = new ArrayList<NetworkInterface>();
        try {
            DescribeNetworkInterfacesRequest request = new DescribeNetworkInterfacesRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("vpc-id").withValues(vpcId));
            request.withFilters(filters);
            return this.listNetworkInterfaces(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<NetworkInterface> listNetworkInterfacesBySubnetId(String subnetId) {
        List<NetworkInterface> lists = new ArrayList<NetworkInterface>();
        try {
            DescribeNetworkInterfacesRequest request = new DescribeNetworkInterfacesRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("subnet-id").withValues(subnetId));
            request.withFilters(filters);
            return this.listNetworkInterfaces(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<NetworkInterface> listNetworkInterfacesByInstanceId(String instanceId) {
        List<NetworkInterface> lists = new ArrayList<NetworkInterface>();
        try {
            DescribeNetworkInterfacesRequest request = new DescribeNetworkInterfacesRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("attachment.instance-id").withValues(instanceId));
            request.withFilters(filters);

            return this.listNetworkInterfaces(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public NetworkInterface selectNetworkInterface(DescribeNetworkInterfacesRequest request) {
        try {
            DescribeNetworkInterfacesResult result = ec2.describeNetworkInterfaces(request);
            if (null != result) {
                if (result.getNetworkInterfaces().size() > 0) {
                    return result.getNetworkInterfaces().get(0);
                }
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public NetworkInterface selectNetworkInterface(String networkInterfaceId) {
        try {

            DescribeNetworkInterfacesRequest request = new DescribeNetworkInterfacesRequest();

            if (null != networkInterfaceId) {
                request.withNetworkInterfaceIds(networkInterfaceId);
                DescribeNetworkInterfacesResult result = ec2.describeNetworkInterfaces(request);

                if (null != result) {
                    if (result.getNetworkInterfaces().size() > 0) {
                        return result.getNetworkInterfaces().get(0);
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

    public boolean modifyNetworkInterfaceAttribute(ModifyNetworkInterfaceAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            ec2.modifyNetworkInterfaceAttribute(request);
            // set tags
            tagHandler.setTags(request.getNetworkInterfaceId(), tags);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public String attachNetworkInterface(AttachNetworkInterfaceRequest request) {
        // return attachmentId
        try {

            //AttachNetworkInterfaceRequest request = new AttachNetworkInterfaceRequest();
            AttachNetworkInterfaceResult result = ec2.attachNetworkInterface(request);

            if (null != result) {
                return result.getAttachmentId();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean detachNetworkInterface(String networkInterfaceId, boolean force) {
        boolean rtn = false;
        try {
            if (null != networkInterfaceId) {
                // describe
                DescribeNetworkInterfacesRequest requestD = new DescribeNetworkInterfacesRequest();
                requestD.withNetworkInterfaceIds(networkInterfaceId);
                DescribeNetworkInterfacesResult result = ec2.describeNetworkInterfaces(requestD);

                if (null != result) {
                    if (result.getNetworkInterfaces().size() > 0) {
                        NetworkInterface networkInterface = result.getNetworkInterfaces().get(0);

                        if (null != networkInterface.getAttachment()) {
                            String attachmentId = networkInterface.getAttachment().getAttachmentId();
                            if (null != attachmentId) {
                                // detach
                                DetachNetworkInterfaceRequest request = new DetachNetworkInterfaceRequest();
                                request.setAttachmentId(attachmentId);
                                request.setForce(new Boolean(force));
                                ec2.detachNetworkInterface(request);
                                rtn = true;
                            }
                        }
                    }
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}