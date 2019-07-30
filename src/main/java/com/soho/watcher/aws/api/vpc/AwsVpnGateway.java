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
import com.amazonaws.services.ec2.model.AttachVpnGatewayRequest;
import com.amazonaws.services.ec2.model.CreateVpnGatewayRequest;
import com.amazonaws.services.ec2.model.CreateVpnGatewayResult;
import com.amazonaws.services.ec2.model.DeleteVpnGatewayRequest;
import com.amazonaws.services.ec2.model.DescribeVpnGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeVpnGatewaysResult;
import com.amazonaws.services.ec2.model.DetachVpnGatewayRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.VpnGateway;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsVpnGateway {

    private static final Logger logger = LoggerFactory.getLogger(AwsVpnGateway.class);

    public AwsVpnGateway(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public VpnGateway createVpnGateway(CreateVpnGatewayRequest request, Map<String, String> tags) {
        try {
            //CreateVpnGatewayRequest request = new CreateVpnGatewayRequest();
            CreateVpnGatewayResult result = ec2.createVpnGateway(request);
            if (result != null) {
                VpnGateway vpnGateway = result.getVpnGateway();
                // set tags
                tagHandler.setTags(vpnGateway.getVpnGatewayId(), tags);
                return vpnGateway;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteVpnGateway(String vpnGatewayId) {
        boolean rtn = false;
        try {

            DeleteVpnGatewayRequest request = new DeleteVpnGatewayRequest();
            request.setVpnGatewayId(vpnGatewayId);
            ec2.deleteVpnGateway(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<VpnGateway> listVpnGateways() {
        List<VpnGateway> lists = new ArrayList<VpnGateway>();
        try {

            //DescribeVpnGatewaysRequest request = new DescribeVpnGatewaysRequest();

            DescribeVpnGatewaysResult result = ec2.describeVpnGateways();


            if (null != result) {
                lists = result.getVpnGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<VpnGateway> listVpnGateways(DescribeVpnGatewaysRequest request) {
        List<VpnGateway> lists = new ArrayList<VpnGateway>();
        try {

            //DescribeVpnGatewaysRequest request = new DescribeVpnGatewaysRequest();

            DescribeVpnGatewaysResult result = ec2.describeVpnGateways(request);


            if (null != result) {
                lists = result.getVpnGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<VpnGateway> listVpnGatewaysByVpcId(String vpcId) {
        List<VpnGateway> lists = new ArrayList<VpnGateway>();
        try {

            DescribeVpnGatewaysRequest request = new DescribeVpnGatewaysRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("attachment.vpc-id").withValues(vpcId));
            request.setFilters(filters);

            return this.listVpnGateways(request);
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public VpnGateway selectVpnGateway(String vpnGatewayId) {
        try {

            DescribeVpnGatewaysRequest request = new DescribeVpnGatewaysRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("vpn-gateway-id").withValues(vpnGatewayId));
            request.setFilters(filters);

            DescribeVpnGatewaysResult result = ec2.describeVpnGateways(request);

            if (null != result) {
                return result.getVpnGateways().get(0);
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public boolean attachVpnGateway(AttachVpnGatewayRequest request) {
        boolean rtn = false;
        try {

            //AttachVpnGatewayRequest request = new AttachVpnGatewayRequest();
            ec2.attachVpnGateway(request);

            rtn = true;


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean detachVpnGateway(DetachVpnGatewayRequest request) {
        boolean rtn = false;
        try {

            //DetachVpnGatewayRequest request = new DetachVpnGatewayRequest();

            ec2.detachVpnGateway(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean detachVpnGateway(String vpnGatewayId) {
        boolean rtn = false;
        try {

            String vpcId = "";

            //
            VpnGateway vpnGateway = selectVpnGateway(vpnGatewayId);
            if (null != vpnGateway) {
                if (vpnGateway.getVpcAttachments().size() > 0) {
                    vpcId = vpnGateway.getVpcAttachments().get(0).getVpcId();
                }
            }

            // detach
            DetachVpnGatewayRequest request = new DetachVpnGatewayRequest();
            request.setVpnGatewayId(vpnGatewayId);
            request.setVpcId(vpcId);
            ec2.detachVpnGateway(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

}
