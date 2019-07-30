package com.soho.watcher.aws.api.analytics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainRequest;
import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainResult;
import com.amazonaws.services.elasticsearch.model.DomainInfo;
import com.amazonaws.services.elasticsearch.model.ElasticsearchDomainStatus;
import com.amazonaws.services.elasticsearch.model.ListDomainNamesRequest;
import com.amazonaws.services.elasticsearch.model.ListDomainNamesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsElasticsearch {

    private static final Logger logger = LoggerFactory.getLogger(AwsElasticsearch.class);

    public AwsElasticsearch(AWSElasticsearch es) {
        this.es = es;
    }

    private AWSElasticsearch es;


    //  function
    public List<DomainInfo> listDomainNames() {

        return listDomainNames(new ListDomainNamesRequest());
    }


    public List<DomainInfo> listDomainNames(ListDomainNamesRequest request) {

        List<DomainInfo> lists = new ArrayList<DomainInfo>();

        try {

            //ListDomainNamesRequest request = new ListDomainNamesRequest();
            ListDomainNamesResult result = es.listDomainNames(request);

            if (null != result) {
                lists = result.getDomainNames();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public ElasticsearchDomainStatus selectElasticsearchDomain(DescribeElasticsearchDomainRequest request) {

        try {

            DescribeElasticsearchDomainResult result = es.describeElasticsearchDomain(request);

            if (null != result) {
                return result.getDomainStatus();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return null;
    }


    public ElasticsearchDomainStatus selectElasticsearchDomain(String domainName) {
        try {
            if (null != domainName && !"".equals(domainName)) {
                DescribeElasticsearchDomainRequest request = new DescribeElasticsearchDomainRequest();
                request.setDomainName(domainName);

                DescribeElasticsearchDomainResult result = es.describeElasticsearchDomain(request);

                if (null != result) {
                    return result.getDomainStatus();
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public String getAccountId(String arn) {
        if (null != arn && !"".equals(arn)) {
            String[] arnStr = arn.split(":");
            if (null != arnStr[4]) {
                return arnStr[4];
            }
        }
        return null;
    }


}