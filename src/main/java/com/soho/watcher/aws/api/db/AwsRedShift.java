package com.soho.watcher.aws.api.db;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.redshift.AmazonRedshift;
import com.amazonaws.services.redshift.model.Cluster;
import com.amazonaws.services.redshift.model.DescribeClustersRequest;
import com.amazonaws.services.redshift.model.DescribeClustersResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsRedShift {

    private static final Logger logger = LoggerFactory.getLogger(AwsRedShift.class);

    public AwsRedShift(AmazonRedshift rs) {
        this.rs = rs;
    }

    private AmazonRedshift rs;


    // function
    public List<Cluster> listClusters() {
        List<Cluster> lists = new ArrayList<Cluster>();
        try {

            DescribeClustersResult result = rs.describeClusters();

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

    public List<Cluster> listClusters(DescribeClustersRequest request) {
        List<Cluster> lists = new ArrayList<Cluster>();
        try {
            //DescribeClustersRequest request = new DescribeClustersRequest();
            DescribeClustersResult result = rs.describeClusters(request);

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
