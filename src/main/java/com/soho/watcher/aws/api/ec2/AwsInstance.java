package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.ModifyInstanceAttributeRequest;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsInstance {

    private static final Logger logger = LoggerFactory.getLogger(AwsInstance.class);


    public AwsInstance(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public Reservation runInstances(RunInstancesRequest request, Map<String, String> tags) {
        try {

            if (null == request.getMonitoring()) {
                request.setMonitoring(true);
            }

            RunInstancesResult result = ec2.runInstances(request);

            // set tags
            if (result != null) {
                Reservation reservation = result.getReservation();


                if (reservation.getInstances().size() > 0) {
                    String instanceId = reservation.getInstances().get(0).getInstanceId();
                    tagHandler.setTags(instanceId, tags);
                    return reservation;
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean modifyInstanceAttribute(ModifyInstanceAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {

            ec2.modifyInstanceAttribute(request);
            rtn = true;

            tagHandler.setTags(request.getInstanceId(), tags);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean terminateInstances(String instanceId) {
        boolean rtn = false;
        try {

            TerminateInstancesRequest request = new TerminateInstancesRequest();

            request.withInstanceIds(instanceId);

            TerminateInstancesResult result = ec2.terminateInstances(request);
            if (null != result) {
                rtn = true;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean stopInstances(String instanceId) {
        boolean rtn = false;
        try {

            StopInstancesRequest request = new StopInstancesRequest();

            request.withInstanceIds(instanceId);

            StopInstancesResult result = ec2.stopInstances(request);
            if (null != result) {
                rtn = true;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean startInstances(String instanceId) {
        boolean rtn = false;
        try {
            StartInstancesRequest request = new StartInstancesRequest();

            request.withInstanceIds(instanceId);

            StartInstancesResult result = ec2.startInstances(request);

            if (null != result) {
                rtn = true;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean rebootInstances(String instanceId) {
        boolean rtn = false;
        try {

            RebootInstancesRequest request = new RebootInstancesRequest();

            request.withInstanceIds(instanceId);
            ec2.rebootInstances(request);
            //RebootInstancesResult result = ec2.rebootInstances(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<Reservation> listInstances() {
        List<Reservation> lists = new ArrayList<Reservation>();
        try {
            DescribeInstancesResult result = ec2.describeInstances();
            if (null != result) {
                lists = result.getReservations();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<Reservation> listInstances(DescribeInstancesRequest request) {
        List<Reservation> lists = new ArrayList<Reservation>();
        try {
            DescribeInstancesResult result = ec2.describeInstances(request);
            if (null != result) {
                lists = result.getReservations();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<Reservation> listInstances(Collection<String> instanceIds) {
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        request.setInstanceIds(instanceIds);
        return listInstances(request);

    }


    public Reservation selectInstance(String instanceId) {
        try {
            DescribeInstancesRequest request = new DescribeInstancesRequest();
            if (null != instanceId) {
/*				
				List<Filter> filters = new Vector<Filter>();
				filters.add(new Filter("instance-id").withValues(instanceId));
				request.setFilters(filters);
				*/
                request.withInstanceIds(instanceId);
                DescribeInstancesResult result = ec2.describeInstances(request);

                if (null != result) {
                    List<Reservation> reservations = result.getReservations();
                    return reservations.get(0);
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