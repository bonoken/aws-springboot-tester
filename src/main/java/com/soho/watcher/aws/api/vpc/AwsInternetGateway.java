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
import com.amazonaws.services.ec2.model.AttachInternetGatewayRequest;
import com.amazonaws.services.ec2.model.CreateInternetGatewayRequest;
import com.amazonaws.services.ec2.model.CreateInternetGatewayResult;
import com.amazonaws.services.ec2.model.DeleteInternetGatewayRequest;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysResult;
import com.amazonaws.services.ec2.model.DetachInternetGatewayRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InternetGateway;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.ec2.AwsEC2TagsHandler;


public class AwsInternetGateway {

    private static final Logger logger = LoggerFactory.getLogger(AwsInternetGateway.class);

    public AwsInternetGateway(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public InternetGateway createInternetGateway(CreateInternetGatewayRequest request, Map<String, String> tags) {
        try {
            //CreateInternetGatewayRequest request = new CreateInternetGatewayRequest();

            CreateInternetGatewayResult result = ec2.createInternetGateway(request);
            if (result != null) {
                InternetGateway internetGateway = result.getInternetGateway();
                // set tags
                tagHandler.setTags(internetGateway.getInternetGatewayId(), tags);
                return internetGateway;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteInternetGateway(String internetGatewayId) {
        boolean rtn = false;
        try {

            this.detachInternetGateway(internetGatewayId);

            // delete
            DeleteInternetGatewayRequest request = new DeleteInternetGatewayRequest();
            request.setInternetGatewayId(internetGatewayId);
            ec2.deleteInternetGateway(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<InternetGateway> listInternetGateways() {
        List<InternetGateway> lists = new ArrayList<InternetGateway>();
        try {

            //DescribeInternetGatewaysRequest request = new DescribeInternetGatewaysRequest();
            DescribeInternetGatewaysResult result = ec2.describeInternetGateways();

            if (null != result) {
                lists = result.getInternetGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<InternetGateway> listInternetGateways(DescribeInternetGatewaysRequest request) {
        List<InternetGateway> lists = new ArrayList<InternetGateway>();
        try {

            //DescribeInternetGatewaysRequest request = new DescribeInternetGatewaysRequest();

            DescribeInternetGatewaysResult result = ec2.describeInternetGateways(request);

            if (null != result) {
                lists = result.getInternetGateways();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<InternetGateway> listInternetGatewaysByVpcId(String vpcId) {
        List<InternetGateway> lists = new ArrayList<InternetGateway>();
        try {

            DescribeInternetGatewaysRequest request = new DescribeInternetGatewaysRequest();
            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("attachment.vpc-id").withValues(vpcId));
            request.setFilters(filters);

            return this.listInternetGateways(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public InternetGateway selectInternetGateway(String internetGatewayId) {
        try {

            DescribeInternetGatewaysRequest request = new DescribeInternetGatewaysRequest();

			/*			List<Filter> filters = new Vector<Filter>();
			filters.add(new Filter("internet-gateway-id").withValues(internetGatewayId));
			request.setFilters(filters);
			 */

            if (null != internetGatewayId) {
                Collection<String> internetGatewayIds = new Vector<String>();
                internetGatewayIds.add(internetGatewayId);

                request.setInternetGatewayIds(internetGatewayIds);
                DescribeInternetGatewaysResult result = ec2.describeInternetGateways(request);

                if (null != result) {
                    if (result.getInternetGateways().size() > 0) {
                        return result.getInternetGateways().get(0);
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


    public boolean attachInternetGateway(AttachInternetGatewayRequest request) {
        try {

            //AttachInternetGatewayRequest request = new AttachInternetGatewayRequest();
            if (null != request.getInternetGatewayId()) {

                //
                this.detachInternetGateway(request.getInternetGatewayId());

                ec2.attachInternetGateway(request);
                return true;
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }


    public boolean detachInternetGateway(DetachInternetGatewayRequest request) {
        boolean rtn = false;
        try {
            ec2.detachInternetGateway(request);

            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean detachInternetGateway(String internetGatewayId) {
        try {
            String vpcId = "";

            InternetGateway internetGateway = selectInternetGateway(internetGatewayId);
            if (null != internetGateway) {
                if (internetGateway.getAttachments().size() > 0) {
                    if (null != internetGateway.getAttachments().get(0).getVpcId()) {
                        vpcId = internetGateway.getAttachments().get(0).getVpcId();

                        // detach
                        DetachInternetGatewayRequest request = new DetachInternetGatewayRequest();
                        request.setInternetGatewayId(internetGatewayId);
                        request.setVpcId(vpcId);
                        ec2.detachInternetGateway(request);
                    }
                }
                return true;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }

}
