package com.soho.watcher.aws.api.network;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.model.GetRestApisRequest;
import com.amazonaws.services.apigateway.model.GetRestApisResult;
import com.amazonaws.services.apigateway.model.RestApi;
import com.soho.watcher.aws.api.AwsException;


public class AwsApiGateway {

    private static final Logger logger = LoggerFactory.getLogger(AwsApiGateway.class);

    public AwsApiGateway(AmazonApiGateway gw) {
        this.gw = gw;
    }

    private AmazonApiGateway gw;


    //  function

    public List<RestApi> listRestApis() {
        List<RestApi> lists = new ArrayList<RestApi>();
        try {
            GetRestApisRequest request = new GetRestApisRequest();
            GetRestApisResult result = gw.getRestApis(request);

            if (null != result) {
                lists = result.getItems();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<RestApi> listRestApis(GetRestApisRequest request) {
        List<RestApi> lists = new ArrayList<RestApi>();
        try {
            GetRestApisResult result = gw.getRestApis(request);

            if (null != result) {
                lists = result.getItems();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}