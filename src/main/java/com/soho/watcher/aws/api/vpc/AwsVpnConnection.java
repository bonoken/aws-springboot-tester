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
import com.amazonaws.services.ec2.model.CreateVpnConnectionRequest;
import com.amazonaws.services.ec2.model.CreateVpnConnectionResult;
import com.amazonaws.services.ec2.model.DeleteVpnConnectionRequest;
import com.amazonaws.services.ec2.model.DescribeVpnConnectionsRequest;
import com.amazonaws.services.ec2.model.DescribeVpnConnectionsResult;
import com.amazonaws.services.ec2.model.VpnConnection;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsVpnConnection {

    private static final Logger logger = LoggerFactory.getLogger(AwsVpnConnection.class);

    public AwsVpnConnection(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public VpnConnection createVpnConnection(CreateVpnConnectionRequest request, Map<String, String> tags) {
        try {
            //CreateVpnConnectionRequest request = new CreateVpnConnectionRequest();
            CreateVpnConnectionResult result = ec2.createVpnConnection(request);
            if (result != null) {
                VpnConnection vpnConnection = result.getVpnConnection();
                // set tags
                tagHandler.setTags(vpnConnection.getVpnConnectionId(), tags);
                return vpnConnection;
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteVpnConnection(String vpnConnectionId) {
        boolean rtn = false;
        try {

            DeleteVpnConnectionRequest request = new DeleteVpnConnectionRequest();
            request.setVpnConnectionId(vpnConnectionId);
            ec2.deleteVpnConnection(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<VpnConnection> listVpnConnections() {
        List<VpnConnection> lists = new ArrayList<VpnConnection>();
        try {

            //DescribeVpnConnectionsRequest request = new DescribeVpnConnectionsRequest();

            DescribeVpnConnectionsResult result = ec2.describeVpnConnections();


            if (null != result) {
                lists = result.getVpnConnections();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<VpnConnection> listVpnConnections(DescribeVpnConnectionsRequest request) {
        List<VpnConnection> lists = new ArrayList<VpnConnection>();
        try {

            //DescribeVpnConnectionsRequest request = new DescribeVpnConnectionsRequest();

            DescribeVpnConnectionsResult result = ec2.describeVpnConnections(request);


            if (null != result) {
                lists = result.getVpnConnections();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public VpnConnection selectVpnConnection(String vpnConnectionId) {
        try {

            DescribeVpnConnectionsRequest request = new DescribeVpnConnectionsRequest();

            if (null != vpnConnectionId) {
                Collection<String> vpnConnectionIds = new Vector<String>();
                vpnConnectionIds.add(vpnConnectionId);

                request.setVpnConnectionIds(vpnConnectionIds);
                DescribeVpnConnectionsResult result = ec2.describeVpnConnections(request);

                if (null != result) {
                    if (result.getVpnConnections().size() > 0) {
                        return result.getVpnConnections().get(0);
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
