package com.soho.watcher.aws.api.elb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancingv2.model.Action;
import com.amazonaws.services.elasticloadbalancingv2.model.Certificate;
import com.amazonaws.services.elasticloadbalancingv2.model.CreateListenerRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.CreateListenerResult;
import com.amazonaws.services.elasticloadbalancingv2.model.CreateLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.CreateLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancingv2.model.DeleteListenerRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.DeleteLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.DeregisterTargetsRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.DescribeListenersRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.DescribeListenersResult;
import com.amazonaws.services.elasticloadbalancingv2.model.DescribeLoadBalancersRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.DescribeLoadBalancersResult;
import com.amazonaws.services.elasticloadbalancingv2.model.Listener;
import com.amazonaws.services.elasticloadbalancingv2.model.LoadBalancer;
import com.amazonaws.services.elasticloadbalancingv2.model.ModifyLoadBalancerAttributesRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.ProtocolEnum;
import com.amazonaws.services.elasticloadbalancingv2.model.RegisterTargetsRequest;
import com.amazonaws.services.elasticloadbalancingv2.model.TargetDescription;
import com.soho.watcher.aws.api.AwsException;

public class AwsLoadBalancerV2 {


    private static final Logger logger = LoggerFactory.getLogger(AwsLoadBalancerV2.class);

    public AwsLoadBalancerV2(AmazonElasticLoadBalancing elb) {
        this.elb = elb;
    }

    private AmazonElasticLoadBalancing elb;


    // function
    public String createLoadBalancer(CreateLoadBalancerRequest request) {
        try {
            //CreateLoadBalancerRequest request = new CreateLoadBalancerRequest();

            CreateLoadBalancerResult result = elb.createLoadBalancer(request);

            if (null != result) {
                String loadBalancerArn = result.getLoadBalancers().get(0).getLoadBalancerArn();
                return loadBalancerArn;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteLoadBalancer(String loadBalancerArn) {
        try {

            DeleteLoadBalancerRequest request = new DeleteLoadBalancerRequest();

            //request.
            request.setLoadBalancerArn(loadBalancerArn);
            elb.deleteLoadBalancer(request);

            return true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }


    public List<LoadBalancer> listLoadBalancers() {
        List<LoadBalancer> lists = new ArrayList<LoadBalancer>();
        try {
            DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
            DescribeLoadBalancersResult result = elb.describeLoadBalancers(request);

            if (null != result) {
                lists = result.getLoadBalancers();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<LoadBalancer> listLoadBalancers(DescribeLoadBalancersRequest request) {
        List<LoadBalancer> lists = new ArrayList<LoadBalancer>();
        try {

            DescribeLoadBalancersResult result = elb.describeLoadBalancers(request);

            if (null != result) {
                lists = result.getLoadBalancers();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public LoadBalancer selectLoadBalancer(String loadBalancerArn) {
        try {

            DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();

            if (null != loadBalancerArn) {
                Collection<String> loadBalancerArns = new Vector<String>();
                loadBalancerArns.add(loadBalancerArn);
                request.setLoadBalancerArns(loadBalancerArns);
                DescribeLoadBalancersResult result = elb.describeLoadBalancers(request);
                List<LoadBalancer> lists = new ArrayList<LoadBalancer>();
                if (null != result) {
                    lists = result.getLoadBalancers();
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
        boolean rtn = false;
        try {
            //ModifyLoadBalancerAttributesRequest request = new ModifyLoadBalancerAttributesRequest();

            elb.modifyLoadBalancerAttributes(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    //  LoadBalancerListener function
    public List<Listener> createListener(String loadBalancerArn, ProtocolEnum protocol, Integer port, Collection<Action> defaultActions, Collection<Certificate> certificates) {
        List<Listener> lists = new ArrayList<Listener>();

        try {

            CreateListenerRequest request = new CreateListenerRequest();
            request.setLoadBalancerArn(loadBalancerArn);

            if (null != certificates) {
                request.setCertificates(certificates);
            }
            if (null != defaultActions) {
                request.setDefaultActions(defaultActions);
            }

            if (null != port) {
                request.setPort(port);
            }
            if (null != protocol) {
                request.setProtocol(protocol);
            }

            CreateListenerResult result = elb.createListener(request);

            if (null != result) {
                lists = result.getListeners();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public boolean deleteListener(String listenerArn) {
        boolean rtn = false;
        try {

            DeleteListenerRequest request = new DeleteListenerRequest();
            request.setListenerArn(listenerArn);
            elb.deleteListener(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<Listener> listListeners(DescribeListenersRequest request) {

        List<Listener> lists = new ArrayList<Listener>();

        try {

            DescribeListenersResult result = elb.describeListeners(request);
            result.getListeners();

            if (null != result) {
                lists = result.getListeners();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;

    }

    // LoadBalancer Targets register function
    public boolean registerTarget(String targetGroupArn, Collection<TargetDescription> targets) {
        boolean rtn = false;
        try {
            RegisterTargetsRequest request = new RegisterTargetsRequest();

            request.setTargetGroupArn(targetGroupArn);
            request.setTargets(targets);
            elb.registerTargets(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public boolean deregisterTarget(String targetGroupArn, Collection<TargetDescription> targets) {
        boolean rtn = false;
        try {
            DeregisterTargetsRequest request = new DeregisterTargetsRequest();

            request.setTargetGroupArn(targetGroupArn);
            request.setTargets(targets);
            elb.deregisterTargets(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}