package com.soho.watcher.aws.api.db;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.soho.watcher.aws.api.AwsException;


public class AwsDynamoDB {

    private static final Logger logger = LoggerFactory.getLogger(AwsDynamoDB.class);

    public AwsDynamoDB(AmazonDynamoDB dynamo) {
        this.dynamo = dynamo;
    }

    private AmazonDynamoDB dynamo;


    // function
    public List<String> listTables() {

        List<String> lists = new ArrayList<String>();
        try {
            //ListTablesRequest request = new ListTablesRequest();
            ListTablesResult result = dynamo.listTables();

            if (null != result) {

                lists = result.getTableNames();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<String> listTables(ListTablesRequest request) {

        List<String> lists = new ArrayList<String>();
        try {
            //ListTablesRequest request = new ListTablesRequest();
            ListTablesResult result = dynamo.listTables(request);

            if (null != result) {

                lists = result.getTableNames();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public TableDescription selectTable(DescribeTableRequest request) {

        try {

            DescribeTableResult result = dynamo.describeTable(request);

            if (null != result) {

                return result.getTable();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


}
