package com.soho.watcher.aws.api.iot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.ListThingsRequest;
import com.amazonaws.services.iot.model.ListThingsResult;
import com.amazonaws.services.iot.model.ThingAttribute;
import com.soho.watcher.aws.api.AwsException;


public class AwsIoT {

    private static final Logger logger = LoggerFactory.getLogger(AwsIoT.class);

    public AwsIoT(AWSIot iot) {
        this.iot = iot;
    }

    private AWSIot iot;


    //  function
    public List<ThingAttribute> listThings() {
        List<ThingAttribute> lists = new ArrayList<ThingAttribute>();

        try {
            ListThingsRequest request = new ListThingsRequest();
            ListThingsResult result = iot.listThings(request);

            if (null != result) {
                lists = result.getThings();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<ThingAttribute> listThings(ListThingsRequest request) {

        List<ThingAttribute> lists = new ArrayList<ThingAttribute>();

        try {
            ListThingsResult result = iot.listThings(request);

            if (null != result) {
                lists = result.getThings();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}