package com.soho.watcher.aws.api.container;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.model.Cluster;
import com.amazonaws.services.ecs.model.ContainerInstance;
import com.amazonaws.services.ecs.model.DescribeContainerInstancesRequest;
import com.amazonaws.services.ecs.model.DescribeContainerInstancesResult;
import com.amazonaws.services.ecs.model.ListClustersResult;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.analytics.AwsEMR;


public class AwsECS {

    private static final Logger logger = LoggerFactory.getLogger(AwsEMR.class);

    public AwsECS(AmazonECS ecs) {
        this.ecs = ecs;
    }

    private AmazonECS ecs;


    //  function
    public List<String> listClusters() {

        List<String> lists = new ArrayList<String>();

        try {

            ListClustersResult result = ecs.listClusters();

            if (null != result) {
                lists = result.getClusterArns();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<ContainerInstance> listContainerInstances(DescribeContainerInstancesRequest request) {

        List<ContainerInstance> lists = new ArrayList<ContainerInstance>();

        try {

            DescribeContainerInstancesResult result = ecs.describeContainerInstances(request);

            if (null != result) {
                lists = result.getContainerInstances();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}