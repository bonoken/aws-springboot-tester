package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.Group;
import com.amazonaws.services.identitymanagement.model.InstanceProfile;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;
import com.amazonaws.services.identitymanagement.model.ListAccountAliasesResult;
import com.amazonaws.services.identitymanagement.model.ListGroupsResult;
import com.amazonaws.services.identitymanagement.model.ListInstanceProfilesResult;
import com.amazonaws.services.identitymanagement.model.ListRolesResult;
import com.amazonaws.services.identitymanagement.model.ListUsersResult;
import com.amazonaws.services.identitymanagement.model.Role;
import com.amazonaws.services.identitymanagement.model.User;
import com.soho.watcher.aws.api.AwsException;


public class AwsIAM {

    private static final Logger logger = LoggerFactory.getLogger(AwsIAM.class);

    public AwsIAM(AmazonIdentityManagement iam) {
        this.iam = iam;
    }

    private AmazonIdentityManagement iam;


    //  function
    public List<Group> listGroups() {
        List<Group> lists = new ArrayList<Group>();
        try {
            ListGroupsResult response = iam.listGroups();

            if (null != response) {
                lists = response.getGroups();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<User> listUsers() {
        List<User> lists = new ArrayList<User>();
        try {
            ListUsersResult response = iam.listUsers();

            if (null != response) {
                lists = response.getUsers();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<AccessKeyMetadata> listAccessKeys() {
        List<AccessKeyMetadata> lists = new ArrayList<AccessKeyMetadata>();
        try {
            ListAccessKeysResult response = iam.listAccessKeys();

            if (null != response) {
                lists = response.getAccessKeyMetadata();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<AccessKeyMetadata> listAccessKeys(ListAccessKeysRequest request) {
        List<AccessKeyMetadata> lists = new ArrayList<AccessKeyMetadata>();
        try {

            ListAccessKeysResult response = iam.listAccessKeys(request);

            if (null != response) {
                lists = response.getAccessKeyMetadata();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<InstanceProfile> listInstanceProfiles() {
        List<InstanceProfile> lists = new ArrayList<InstanceProfile>();
        try {

            ListInstanceProfilesResult response = iam.listInstanceProfiles();

            if (null != response) {
                lists = response.getInstanceProfiles();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


    public List<Role> listRoles() {
        List<Role> lists = new ArrayList<Role>();
        try {

            ListRolesResult response = iam.listRoles();

            if (null != response) {
                lists = response.getRoles();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }


}