package com.soho.watcher.aws.api;

import org.slf4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;


public class AwsException {

    public static void printLog(Logger logger, AmazonServiceException ase) {
        ase.printStackTrace();
        logger.error("Error Message : " + ase.getMessage());
        logger.error("HTTP Status Code : " + ase.getStatusCode());
        logger.error("AWS Error Code : " + ase.getErrorCode());
        logger.error("Error Type : " + ase.getErrorType());
        logger.error("Request ID : " + ase.getRequestId());
    }

    public static void printLog(Logger logger, AmazonClientException ace) {
        ace.printStackTrace();
        logger.error("Error Message: " + ace.getMessage());
    }
}
