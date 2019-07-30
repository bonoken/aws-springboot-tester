package com.soho.watcher.aws.api.elb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.model.CreateLoadBalancerListenersRequest;
import com.amazonaws.services.elasticloadbalancing.model.CreateLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.CreateLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancing.model.DeleteLoadBalancerListenersRequest;
import com.amazonaws.services.elasticloadbalancing.model.DeleteLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.DeregisterInstancesFromLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.DeregisterInstancesFromLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersRequest;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersResult;
import com.amazonaws.services.elasticloadbalancing.model.Instance;
import com.amazonaws.services.elasticloadbalancing.model.Listener;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.ModifyLoadBalancerAttributesRequest;
import com.amazonaws.services.elasticloadbalancing.model.RegisterInstancesWithLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.RegisterInstancesWithLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancing.model.SetLoadBalancerListenerSSLCertificateRequest;
import com.soho.watcher.aws.api.AwsException;

public class AwsLoadBalancer {

    private static final Logger logger = LoggerFactory.getLogger(AwsLoadBalancer.class);

    public AwsLoadBalancer(AmazonElasticLoadBalancing elb) {
        this.elb = elb;
    }

    private AmazonElasticLoadBalancing elb;


    // function
    public String createLoadBalancer(CreateLoadBalancerRequest request) {
        try {

            //CreateLoadBalancerRequest request = new CreateLoadBalancerRequest();

            CreateLoadBalancerResult result = elb.createLoadBalancer(request);

            if (null != result) {
                String dNSName = result.getDNSName();
                return dNSName;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteLoadBalancer(String loadBalancerName) {

        try {

            DeleteLoadBalancerRequest request = new DeleteLoadBalancerRequest();

            request.setLoadBalancerName(loadBalancerName);

            elb.deleteLoadBalancer(request);

            return true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }

    public List<LoadBalancerDescription> listLoadBalancers() {
        List<LoadBalancerDescription> lists = new ArrayList<LoadBalancerDescription>();
        try {

            //DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
            DescribeLoadBalancersResult result = elb.describeLoadBalancers();

            if (null != result) {
                lists = result.getLoadBalancerDescriptions();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<LoadBalancerDescription> listLoadBalancers(DescribeLoadBalancersRequest request) {
        List<LoadBalancerDescription> lists = new ArrayList<LoadBalancerDescription>();
        try {
            DescribeLoadBalancersResult result = elb.describeLoadBalancers(request);

            if (null != result) {
                lists = result.getLoadBalancerDescriptions();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public LoadBalancerDescription selectLoadBalancer(String loadBalancerName) {
        try {
            DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();

            if (null != loadBalancerName) {
                Collection<String> loadBalancerNames = new Vector<String>();
                loadBalancerNames.add(loadBalancerName);
                request.setLoadBalancerNames(loadBalancerNames);
                DescribeLoadBalancersResult result = elb.describeLoadBalancers(request);
                List<LoadBalancerDescription> lists = new ArrayList<LoadBalancerDescription>();
                if (null != result) {
                    lists = result.getLoadBalancerDescriptions();
                    if (lists.size() > 0) {
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

    public boolean modifyLoadBalancerAttributes(ModifyLoadBalancerAttributesRequest request) {
        try {
            //ModifyLoadBalancerAttributesRequest request = new ModifyLoadBalancerAttributesRequest();

            elb.modifyLoadBalancerAttributes(request);

            return true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }


    //  LoadBalancerListener function
    public boolean createLoadBalancerListeners(String loadBalancerName, Collection<Listener> listeners) {
        boolean rtn = false;
        try {
            CreateLoadBalancerListenersRequest request = new CreateLoadBalancerListenersRequest();
            request.setLoadBalancerName(loadBalancerName);
            request.setListeners(listeners);
            //CreateLoadBalancerListenersResult result = new CreateLoadBalancerListenersResult();
            elb.createLoadBalancerListeners(request);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean deleteLoadBalancerListeners(String loadBalancerName, Collection<Integer> loadBalancerPorts) {
        boolean rtn = false;
        try {
            DeleteLoadBalancerListenersRequest request = new DeleteLoadBalancerListenersRequest();
            request.setLoadBalancerName(loadBalancerName);
            request.setLoadBalancerPorts(loadBalancerPorts);

            //DeleteLoadBalancerListenersResult result = new DeleteLoadBalancerListenersResult();
            elb.deleteLoadBalancerListeners(request);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean setLoadBalancerListenerSSLCertificate(SetLoadBalancerListenerSSLCertificateRequest request) {
        boolean rtn = false;
        try {

            //SetLoadBalancerListenerSSLCertificateRequest request = new SetLoadBalancerListenerSSLCertificateRequest();

            //SetLoadBalancerListenerSSLCertificateResult result = new SetLoadBalancerListenerSSLCertificateResult();
            elb.setLoadBalancerListenerSSLCertificate(request);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    // LoadBalancer Member instances function

    public Collection<String> registerInstancesWithLoadBalancer(String loadBalancerName, Collection<String> members) {
        Collection<String> instanceIds = new Vector<String>();
        try {
            RegisterInstancesWithLoadBalancerRequest request = new RegisterInstancesWithLoadBalancerRequest();

            if (null != members) {
                Collection<Instance> instances = new Vector<Instance>();

                for (String instanceId : members) {
                    Instance instance = new Instance();
                    instance.setInstanceId(instanceId);
                    instances.add(instance);
                }

                request.setLoadBalancerName(loadBalancerName);
                request.setInstances(instances);
                RegisterInstancesWithLoadBalancerResult result = elb.registerInstancesWithLoadBalancer(request);

                if (null != result) {
                    for (com.amazonaws.services.elasticloadbalancing.model.Instance remainingInstance : result.getInstances()) {
                        instanceIds.add(remainingInstance.getInstanceId());
                    }
                    if (instanceIds.size() > 0) {
                        return instanceIds;
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

    public Collection<String> deregisterInstancesFromLoadBalancer(String loadBalancerName, Collection<String> members) {
        Collection<String> instanceIds = new Vector<String>();
        try {
            DeregisterInstancesFromLoadBalancerRequest request = new DeregisterInstancesFromLoadBalancerRequest();

            if (null != members) {
                Collection<Instance> instances = new Vector<Instance>();

                for (String instanceId : members) {
                    Instance instance = new Instance();
                    instance.setInstanceId(instanceId);
                    instances.add(instance);
                }
                request.setLoadBalancerName(loadBalancerName);
                request.setInstances(instances);

                DeregisterInstancesFromLoadBalancerResult result = elb.deregisterInstancesFromLoadBalancer(request);

                if (null != result) {
                    for (com.amazonaws.services.elasticloadbalancing.model.Instance remainingInstance : result.getInstances()) {
                        instanceIds.add(remainingInstance.getInstanceId());
                    }
                    return instanceIds;
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