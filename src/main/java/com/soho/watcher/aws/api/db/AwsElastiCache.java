package com.soho.watcher.aws.api.db;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticache.AmazonElastiCache;
import com.amazonaws.services.elasticache.model.CacheCluster;
import com.amazonaws.services.elasticache.model.DescribeCacheClustersRequest;
import com.amazonaws.services.elasticache.model.DescribeCacheClustersResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsElastiCache {

    private static final Logger logger = LoggerFactory.getLogger(AwsElastiCache.class);

    public AwsElastiCache(AmazonElastiCache ec) {
        this.ec = ec;
    }

    private AmazonElastiCache ec;


    // function
    public List<CacheCluster> listCacheClusters() {
        List<CacheCluster> lists = new ArrayList<CacheCluster>();
        try {

            DescribeCacheClustersResult result = ec.describeCacheClusters();

            if (null != result) {
                lists = result.getCacheClusters();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<CacheCluster> listCacheClusters(DescribeCacheClustersRequest request) {
        List<CacheCluster> lists = new ArrayList<CacheCluster>();
        try {

            DescribeCacheClustersResult result = ec.describeCacheClusters(request);

            if (null != result) {
                lists = result.getCacheClusters();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
