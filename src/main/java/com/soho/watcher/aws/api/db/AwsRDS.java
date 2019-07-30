package com.soho.watcher.aws.api.db;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DeleteDBInstanceRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.soho.watcher.aws.api.AwsException;


public class AwsRDS {

    private static final Logger logger = LoggerFactory.getLogger(AwsRDS.class);

    public AwsRDS(AmazonRDS rds) {
        this.rds = rds;
    }

    private AmazonRDS rds;


    // function
    public DBInstance createDBInstance(CreateDBInstanceRequest request) {
        try {

            DBInstance dbInstance = rds.createDBInstance(request);

            return dbInstance;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public DBInstance deleteDBInstance(DeleteDBInstanceRequest request) {

        try {
            DBInstance dbInstance = rds.deleteDBInstance(request);

            return dbInstance;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }

    public List<DBInstance> listDBInstances() {
        List<DBInstance> lists = new ArrayList<DBInstance>();
        try {

            DescribeDBInstancesResult result = rds.describeDBInstances();

            if (null != result) {
                lists = result.getDBInstances();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<DBInstance> listDBInstances(DescribeDBInstancesRequest request) {
        List<DBInstance> lists = new ArrayList<DBInstance>();

        try {
            // DescribeDBInstancesRequest request = new DescribeDBInstancesRequest();
            DescribeDBInstancesResult result = rds.describeDBInstances(request);

            if (null != result) {
                lists = result.getDBInstances();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


}
