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
import com.amazonaws.services.ec2.model.AssociateRouteTableRequest;
import com.amazonaws.services.ec2.model.AssociateRouteTableResult;
import com.amazonaws.services.ec2.model.CreateRouteRequest;
import com.amazonaws.services.ec2.model.CreateRouteResult;
import com.amazonaws.services.ec2.model.CreateRouteTableRequest;
import com.amazonaws.services.ec2.model.CreateRouteTableResult;
import com.amazonaws.services.ec2.model.DeleteRouteRequest;
import com.amazonaws.services.ec2.model.DeleteRouteTableRequest;
import com.amazonaws.services.ec2.model.DescribeRouteTablesRequest;
import com.amazonaws.services.ec2.model.DescribeRouteTablesResult;
import com.amazonaws.services.ec2.model.DisableVgwRoutePropagationRequest;
import com.amazonaws.services.ec2.model.DisassociateRouteTableRequest;
import com.amazonaws.services.ec2.model.EnableVgwRoutePropagationRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.ReplaceRouteRequest;
import com.amazonaws.services.ec2.model.ReplaceRouteTableAssociationRequest;
import com.amazonaws.services.ec2.model.ReplaceRouteTableAssociationResult;
import com.amazonaws.services.ec2.model.RouteTable;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsRouteTable {

    private static final Logger logger = LoggerFactory.getLogger(AwsRouteTable.class);

    public AwsRouteTable(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  RouteTable function
    public RouteTable createRouteTable(String vpcId, Map<String, String> tags) {
        try {
            CreateRouteTableRequest request = new CreateRouteTableRequest();
            request.setVpcId(vpcId);

            CreateRouteTableResult result = ec2.createRouteTable(request);

            if (result != null) {
                RouteTable routeTable = result.getRouteTable();
                // set tags
                tagHandler.setTags(routeTable.getRouteTableId(), tags);
                return routeTable;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteRouteTable(String routeTableId) {
        boolean rtn = false;
        try {
            DeleteRouteTableRequest request = new DeleteRouteTableRequest();
            request.setRouteTableId(routeTableId);
            ec2.deleteRouteTable(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<RouteTable> listRouteTables() {
        List<RouteTable> lists = new ArrayList<RouteTable>();
        try {
            //DescribeRouteTablesRequest request = new DescribeRouteTablesRequest();
            DescribeRouteTablesResult result = ec2.describeRouteTables();

            if (null != result) {
                lists = result.getRouteTables();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<RouteTable> listRouteTables(DescribeRouteTablesRequest request) {
        List<RouteTable> lists = new ArrayList<RouteTable>();
        try {
            //DescribeRouteTablesRequest request = new DescribeRouteTablesRequest();

            DescribeRouteTablesResult result = ec2.describeRouteTables(request);

            if (null != result) {
                lists = result.getRouteTables();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<RouteTable> listRouteTablesByVpcId(String vpcId) {
        List<RouteTable> lists = new ArrayList<RouteTable>();
        try {
            DescribeRouteTablesRequest request = new DescribeRouteTablesRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("vpc-id").withValues(vpcId));
            request.setFilters(filters);

            return this.listRouteTables(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public RouteTable selectRouteTable(String routeTableId) {
        try {
            DescribeRouteTablesRequest request = new DescribeRouteTablesRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("route-table-id").withValues(routeTableId));
            request.setFilters(filters);

            DescribeRouteTablesResult result = ec2.describeRouteTables(request);

            List<RouteTable> lists = new ArrayList<RouteTable>();

            if (null != result) {
                lists = result.getRouteTables();
                if (lists.size() > 0) {
                    if (null != lists.get(0)) {
                        return lists.get(0);
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

    public RouteTable selectRouteTableBySubnetId(String subnetId) {
        try {
            DescribeRouteTablesRequest request = new DescribeRouteTablesRequest();

            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("association.subnet-id").withValues(subnetId));
            request.setFilters(filters);

            DescribeRouteTablesResult result = ec2.describeRouteTables(request);

            List<RouteTable> lists = new ArrayList<RouteTable>();

            if (null != result) {
                lists = result.getRouteTables();
                if (lists.size() > 0) {
                    if (null != lists.get(0)) {
                        return lists.get(0);
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


    // Route Method
    public boolean createRoute(CreateRouteRequest request) {
        boolean rtn = false;
        try {
            CreateRouteResult result = ec2.createRoute(request);
            if (null != result) {
                rtn = result.getReturn();
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean deleteRoute(DeleteRouteRequest request) {
        boolean rtn = false;
        try {

            //DeleteRouteRequest request = new DeleteRouteRequest();

            ec2.deleteRoute(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean replaceRoute(ReplaceRouteRequest request) {
        boolean rtn = false;
        try {

            //ReplaceRouteRequest request = new ReplaceRouteRequest();

            ec2.replaceRoute(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    // SUBNET Associate Route Table Method
    public String associateRouteTable(AssociateRouteTableRequest request) {
        try {

            //AssociateRouteTableRequest request = new AssociateRouteTableRequest();

            AssociateRouteTableResult result = ec2.associateRouteTable(request);
            if (null != result) {
                String associationId = result.getAssociationId();

                return associationId;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public boolean disassociateRouteTable(String associationId) {
        boolean rtn = false;
        try {

            DisassociateRouteTableRequest request = new DisassociateRouteTableRequest();
            request.setAssociationId(associationId);

            ec2.disassociateRouteTable(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public String replaceRouteTableAssociation(ReplaceRouteTableAssociationRequest request) {
        try {

            //ReplaceRouteTableAssociationRequest request = new ReplaceRouteTableAssociationRequest();

            ReplaceRouteTableAssociationResult result = ec2.replaceRouteTableAssociation(request);

            if (null != result) {
                String associationId = result.getNewAssociationId();
                return associationId;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    // VPN Gateway Route Propagation
    public boolean enableVgwRoutePropagation(EnableVgwRoutePropagationRequest request) {
        boolean rtn = false;
        try {

            //EnableVgwRoutePropagationRequest request = new EnableVgwRoutePropagationRequest();

            ec2.enableVgwRoutePropagation(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean disableVgwRoutePropagation(DisableVgwRoutePropagationRequest request) {
        boolean rtn = false;
        try {

            //DisableVgwRoutePropagationRequest request = new DisableVgwRoutePropagationRequest();

            ec2.disableVgwRoutePropagation(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}