package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.AllocateAddressRequest;
import com.amazonaws.services.ec2.model.AllocateAddressResult;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.AssociateAddressResult;
import com.amazonaws.services.ec2.model.DescribeAddressesRequest;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DisassociateAddressRequest;
import com.amazonaws.services.ec2.model.DomainType;
import com.amazonaws.services.ec2.model.ReleaseAddressRequest;
import com.soho.watcher.aws.api.AwsException;

public class AwsElasticIp {

    private static final Logger logger = LoggerFactory.getLogger(AwsInstance.class);

    public AwsElasticIp(AmazonEC2 ec2) {
        this.ec2 = ec2;
    }

    private AmazonEC2 ec2;


    //  function
    public String allocateAddress() {

        // return AllocationId

        try {
            AllocateAddressRequest request = new AllocateAddressRequest();

            request.setDomain(DomainType.Vpc);
            AllocateAddressResult result = ec2.allocateAddress(request);
            if (result != null) {
                return result.getAllocationId();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean releaseAddress(ReleaseAddressRequest request) {
        boolean rtn = false;
        try {
            ec2.releaseAddress(request);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public boolean releaseAddress(String publicIp) {
        boolean rtn = false;
        try {
            ReleaseAddressRequest request = new ReleaseAddressRequest();
            request.setPublicIp(publicIp);
            ec2.releaseAddress(request);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<Address> listAddresses(DescribeAddressesRequest request) {

        List<Address> lists = new ArrayList<Address>();

        try {
            DescribeAddressesResult result = ec2.describeAddresses(request);

            if (null != result) {
                lists = result.getAddresses();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<Address> listAddresses() {
        List<Address> lists = new ArrayList<Address>();
        try {

            DescribeAddressesResult result = ec2.describeAddresses();

            if (null != result) {
                lists = result.getAddresses();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public Address selectAddress(DescribeAddressesRequest request) {
        try {

            //DescribeAddressesRequest request = new DescribeAddressesRequest();

            DescribeAddressesResult result = ec2.describeAddresses(request);

            if (null != result) {
                if (result.getAddresses().size() > 0) {
                    return result.getAddresses().get(0);
                }
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public Address selectAddressByPublicIp(String publicIp) {
        try {

            DescribeAddressesRequest request = new DescribeAddressesRequest();
            request.withPublicIps(publicIp);

            return this.selectAddress(request);


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public Address selectAddressByAllocationId(String allocationId) {
        try {

            DescribeAddressesRequest request = new DescribeAddressesRequest();
            request.withAllocationIds(allocationId);

            return this.selectAddress(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public String associateAddress(AssociateAddressRequest request) {
        // return associationId
        try {

            //AssociateAddressRequest request = new AssociateAddressRequest();

            AssociateAddressResult result = ec2.associateAddress(request);

            if (null != result) {
                return result.getAssociationId();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean disassociateAddress(DisassociateAddressRequest request) {
        boolean rtn = false;
        try {

            ec2.disassociateAddress(request);
            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return rtn;
    }


}