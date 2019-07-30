package com.soho.watcher.aws.api.serverless;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.model.EventSourceMappingConfiguration;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.amazonaws.services.lambda.model.GetFunctionResult;
import com.amazonaws.services.lambda.model.ListEventSourceMappingsResult;
import com.amazonaws.services.lambda.model.ListFunctionsRequest;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsLambda {

    private static final Logger logger = LoggerFactory.getLogger(AwsLambda.class);

    public AwsLambda(com.amazonaws.services.lambda.AWSLambda lambda) {
        this.lambda = lambda;
    }

    private com.amazonaws.services.lambda.AWSLambda lambda;


    //  function
    public List<FunctionConfiguration> listFunctions() {
        List<FunctionConfiguration> lists = new ArrayList<FunctionConfiguration>();
        try {
            ListFunctionsResult result = lambda.listFunctions();

            if (null != result) {
                lists = result.getFunctions();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<FunctionConfiguration> listFunctions(ListFunctionsRequest request) {
        List<FunctionConfiguration> lists = new ArrayList<FunctionConfiguration>();
        try {
            //ListFunctionsRequest request= new ListFunctionsRequest();
            ListFunctionsResult result = lambda.listFunctions(request);

            if (null != result) {
                lists = result.getFunctions();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<EventSourceMappingConfiguration> listEventSourceMappings() {
        List<EventSourceMappingConfiguration> lists = new ArrayList<EventSourceMappingConfiguration>();
        try {

            ListEventSourceMappingsResult result = lambda.listEventSourceMappings();

            if (null != result) {
                lists = result.getEventSourceMappings();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public GetFunctionResult getFunction(GetFunctionRequest request) {
        try {
            GetFunctionResult result = lambda.getFunction(request);

            if (null != result) {
                return result;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return null;
    }


}