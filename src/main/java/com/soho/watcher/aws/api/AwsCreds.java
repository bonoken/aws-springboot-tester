package com.soho.watcher.aws.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;


public class AwsCreds implements AWSCredentialsProvider {

    private static final Logger logger = LoggerFactory.getLogger(AwsClientFactory.class);

    public AwsCreds(AwsConfiguration config) {
        this.awsConfig = config;
    }

    protected AwsConfiguration awsConfig;


    public AWSCredentialsProvider getDefaultCredentialsProvider() {
        AWSCredentialsProvider provider = null;

        provider = this.getCredentialsProvider(this.awsConfig.getAccessKey(), this.awsConfig.getSecretKey());

        return provider;
    }

    public AWSCredentialsProvider getCredentialsProvider(String accessKey, String secretKey) {
        AWSCredentialsProvider provider = null;

        if (accessKey != null && accessKey.length() > 0 && secretKey != null && secretKey.length() > 0) {
            provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
        } else {
            provider = new DefaultAWSCredentialsProviderChain();
        }
        return provider;
    }


    @Override
    public AWSCredentials getCredentials() {
        return this.getDefaultCredentialsProvider().getCredentials();
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
    }


}