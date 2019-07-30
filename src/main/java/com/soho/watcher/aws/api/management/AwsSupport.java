package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.model.CaseDetails;
import com.amazonaws.services.support.model.DescribeCasesRequest;
import com.amazonaws.services.support.model.DescribeCasesResult;
import com.amazonaws.services.support.model.DescribeServicesRequest;
import com.amazonaws.services.support.model.DescribeServicesResult;
import com.amazonaws.services.support.model.DescribeSeverityLevelsRequest;
import com.amazonaws.services.support.model.DescribeSeverityLevelsResult;
import com.amazonaws.services.support.model.Service;
import com.amazonaws.services.support.model.SeverityLevel;
import com.soho.watcher.aws.api.AwsException;


public class AwsSupport {

    private static final Logger logger = LoggerFactory.getLogger(AwsSupport.class);

    public AwsSupport(AWSSupport support) {
        this.support = support;
    }

    private AWSSupport support;


    //  function
    public List<CaseDetails> listCases() {

        List<CaseDetails> lists = new ArrayList<CaseDetails>();

        try {
            DescribeCasesResult response = support.describeCases();
            if (null != response) {
                lists = response.getCases();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<CaseDetails> listCases(DescribeCasesRequest request) {

        List<CaseDetails> lists = new ArrayList<CaseDetails>();

        try {
            DescribeCasesResult response = support.describeCases(request);
            if (null != response) {
                lists = response.getCases();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<Service> listServices() {

        List<Service> lists = new ArrayList<Service>();

        try {
            DescribeServicesResult response = support.describeServices();
            if (null != response) {
                lists = response.getServices();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<Service> listServices(DescribeServicesRequest request) {

        List<Service> lists = new ArrayList<Service>();

        try {
            DescribeServicesResult response = support.describeServices(request);
            if (null != response) {
                lists = response.getServices();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<SeverityLevel> listSeverityLevels() {

        List<SeverityLevel> lists = new ArrayList<SeverityLevel>();

        try {
            DescribeSeverityLevelsResult response = support.describeSeverityLevels();
            if (null != response) {
                lists = response.getSeverityLevels();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<SeverityLevel> listSeverityLevels(DescribeSeverityLevelsRequest request) {

        List<SeverityLevel> lists = new ArrayList<SeverityLevel>();

        try {
            DescribeSeverityLevelsResult response = support.describeSeverityLevels(request);
            if (null != response) {
                lists = response.getSeverityLevels();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}