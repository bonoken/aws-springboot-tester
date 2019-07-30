package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.ImportKeyPairRequest;
import com.amazonaws.services.ec2.model.ImportKeyPairResult;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.soho.watcher.aws.api.AwsException;

public class AwsKeypair {

    private static final Logger logger = LoggerFactory.getLogger(AwsKeypair.class);

    public AwsKeypair(AmazonEC2 ec2) {
        this.ec2 = ec2;
    }

    private AmazonEC2 ec2;

    //  function
    public KeyPair createKeyPair(String keyName) {
        try {
            CreateKeyPairRequest request = new CreateKeyPairRequest();

            request.setKeyName(keyName);
            CreateKeyPairResult result = ec2.createKeyPair(request);

            if (null != result) {
                return result.getKeyPair();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteKeyPair(String keyName) {
        boolean rtn = false;
        try {
            DeleteKeyPairRequest request = new DeleteKeyPairRequest();

            request.setKeyName(keyName);
            ec2.deleteKeyPair(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<KeyPairInfo> listKeyPairs() {
        List<KeyPairInfo> lists = new ArrayList<KeyPairInfo>();
        try {

            //DescribeKeyPairsRequest request = new DescribeKeyPairsRequest();
            DescribeKeyPairsResult result = ec2.describeKeyPairs();

            if (null != result) {
                lists = result.getKeyPairs();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<KeyPairInfo> listKeyPairs(DescribeKeyPairsRequest request) {
        List<KeyPairInfo> lists = new ArrayList<KeyPairInfo>();
        try {

            DescribeKeyPairsResult result = ec2.describeKeyPairs(request);

            if (null != result) {
                lists = result.getKeyPairs();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public KeyPairInfo selectKeyPair(String keyName) {
        try {

            DescribeKeyPairsRequest request = new DescribeKeyPairsRequest();

            if (null != keyName) {
				/*			
			List<Filter> filters = new Vector<Filter>();
			filters.add(new Filter("key-name").withValues(keyName));
			request.setFilters(filters);
				 */
                request.withKeyNames(keyName);
                DescribeKeyPairsResult result = ec2.describeKeyPairs(request);

                if (null != result) {
                    if (result.getKeyPairs().size() > 0) {
                        return result.getKeyPairs().get(0);
                    }
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public ImportKeyPairResult importKeyPair(ImportKeyPairRequest request) {
        try {

            //ImportKeyPairRequest request = new ImportKeyPairRequest();

            ImportKeyPairResult result = ec2.importKeyPair(request);

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
