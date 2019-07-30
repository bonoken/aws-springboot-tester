package com.soho.watcher.aws.api.analytics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.ListClustersRequest;
import com.amazonaws.services.elasticmapreduce.model.ListClustersResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsEMR {

    private static final Logger logger = LoggerFactory.getLogger(AwsEMR.class);

    public AwsEMR(AmazonElasticMapReduce emr) {
        this.emr = emr;
    }

    private AmazonElasticMapReduce emr;


    //  function
    public List<ClusterSummary> listClusters() {

        List<ClusterSummary> lists = new ArrayList<ClusterSummary>();

        try {

            ListClustersResult result = emr.listClusters();

            if (null != result) {
                lists = result.getClusters();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<ClusterSummary> listClusters(ListClustersRequest request) {

        List<ClusterSummary> lists = new ArrayList<ClusterSummary>();

        try {

            ListClustersResult result = emr.listClusters(request);

            if (null != result) {
                lists = result.getClusters();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}