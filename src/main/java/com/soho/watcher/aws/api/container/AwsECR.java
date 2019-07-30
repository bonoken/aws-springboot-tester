package com.soho.watcher.aws.api.container;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.model.DescribeImagesRequest;
import com.amazonaws.services.ecr.model.DescribeImagesResult;
import com.amazonaws.services.ecr.model.DescribeRepositoriesRequest;
import com.amazonaws.services.ecr.model.DescribeRepositoriesResult;
import com.amazonaws.services.ecr.model.ImageDetail;
import com.amazonaws.services.ecr.model.Repository;
import com.soho.watcher.aws.api.AwsException;
import com.soho.watcher.aws.api.analytics.AwsEMR;


public class AwsECR {

    private static final Logger logger = LoggerFactory.getLogger(AwsEMR.class);

    public AwsECR(AmazonECR ecr) {
        this.ecr = ecr;
    }

    private AmazonECR ecr;


    //  function
    public List<Repository> listRepositories() {

        return listRepositories(new DescribeRepositoriesRequest());
    }


    public List<Repository> listRepositories(DescribeRepositoriesRequest request) {

        List<Repository> lists = new ArrayList<Repository>();

        try {

            DescribeRepositoriesResult result = ecr.describeRepositories(request);

            if (null != result) {
                lists = result.getRepositories();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<ImageDetail> listImages(DescribeImagesRequest request) {

        List<ImageDetail> lists = new ArrayList<ImageDetail>();

        try {

            DescribeImagesResult result = ecr.describeImages(request);

            if (null != result) {
                lists = result.getImageDetails();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}