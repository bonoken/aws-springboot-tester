package com.soho.watcher.aws.api;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClient;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClient;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudtrail.AWSCloudTrail;
import com.amazonaws.services.cloudtrail.AWSCloudTrailClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.codedeploy.AmazonCodeDeploy;
import com.amazonaws.services.codedeploy.AmazonCodeDeployClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.AmazonECRClient;
import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClient;
import com.amazonaws.services.elasticache.AmazonElastiCache;
import com.amazonaws.services.elasticache.AmazonElastiCacheClient;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalkClient;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancingv2.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClient;
import com.amazonaws.services.glacier.AmazonGlacier;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.health.AWSHealth;
import com.amazonaws.services.health.AWSHealthClient;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.pricing.AWSPricing;
import com.amazonaws.services.pricing.AWSPricingClient;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.redshift.AmazonRedshift;
import com.amazonaws.services.redshift.AmazonRedshiftClient;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.AWSSupportClient;
import com.amazonaws.services.waf.AWSWAF;
import com.amazonaws.services.waf.AWSWAFClient;


public class AwsClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(AwsClientFactory.class);

    public AwsClientFactory(AwsConfiguration config) {
        this.awsConfig = config;
        this.awsCreds = new AwsCreds(config);
        this.defaultProvider = new AtomicReference<AWSCredentialsProvider>(awsCreds.getDefaultCredentialsProvider());
    }

    private AwsConfiguration awsConfig;
    private AwsCreds awsCreds;
    private AtomicReference<AWSCredentialsProvider> defaultProvider;


    public void setDefaultCredentialsProvider(AWSCredentialsProvider provider) {
        this.defaultProvider.set(provider);
    }


    private ClientConfiguration clientConfig(AwsConfiguration config) {
        final int maxRetries = config.getMaxErrorRetry();
        return new ClientConfiguration()
                .withConnectionTimeout((int) (config.getConnectionTimeout()) * 1000)
                .withMaxConnections((int) config.getMaxConnections())
                .withMaxErrorRetry((int) config.getMaxErrorRetry())
                .withSocketTimeout((int) (config.getSocketTimeout()) * 1000)
                .withRetryPolicy(
                        new RetryPolicy(
                                new RetryPolicy.RetryCondition() {
                                    @Override
                                    public boolean shouldRetry(
                                            AmazonWebServiceRequest r, AmazonClientException e, int retriesAttempted
                                    ) {
                                        if (e instanceof AmazonServiceException) {
                                            int code = ((AmazonServiceException) e).getStatusCode();
                                            if (!(code % 100 == 5 || code == 400 || code == 403 || code == 429))
                                                return false;
                                        }
                                        return retriesAttempted < maxRetries;
                                    }
                                },
                                new RetryPolicy.BackoffStrategy() {
                                    @Override
                                    public long delayBeforeNextRetry(
                                            AmazonWebServiceRequest r, AmazonClientException e, int retriesAttempted
                                    ) {
                                        return retriesAttempted * 1000L;
                                    }
                                },
                                config.getMaxErrorRetry(),
                                true
                        )
                );
    }

    // AWS health (us-east-1)
    public AWSHealth healthClient() {
        return healthClient(this.defaultProvider.get());
    }

    public AWSHealth healthClient(AWSCredentialsProvider provider) {
        AWSHealth client = AWSHealthClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(this.awsConfig))
                .withRegion("us-east-1")
                .build();
        return client;
    }

    // AWS Support (us-east-1)
    public AWSSupport supportClient() {
        return supportClient(this.defaultProvider.get());
    }

    public AWSSupport supportClient(AWSCredentialsProvider provider) {
        AWSSupport client = AWSSupportClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(this.awsConfig))
                .withRegion("us-east-1")
                .build();
        return client;
    }


    // AWS Pricing (us-east-1/ap-south-1)
    public AWSPricing pricingClient() {
        return pricingClient(this.defaultProvider.get());
    }

    public AWSPricing pricingClient(String region) {
        return pricingClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSPricing pricingClient(AWSCredentialsProvider provider) {
        return pricingClient(this.awsConfig, provider, "us-east-1");
    }

    public AWSPricing pricingClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {

        AWSPricing client = AWSPricingClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // AWS IAM
    public AmazonIdentityManagement iamClient() {
        return iamClient(this.defaultProvider.get());
    }

    public AmazonIdentityManagement iamClient(String region) {
        return iamClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AmazonIdentityManagement iamClient(AWSCredentialsProvider provider) {
        return iamClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonIdentityManagement iamClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {

        AmazonIdentityManagement client = AmazonIdentityManagementClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // Cloud Trail
    public AWSCloudTrail cloudTrailClient() {
        return cloudTrailClient(this.defaultProvider.get());
    }

    public AWSCloudTrail cloudTrailClient(String region) {
        return cloudTrailClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AWSCloudTrail cloudTrailClient(AWSCredentialsProvider provider) {
        return cloudTrailClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSCloudTrail cloudTrailClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {

        AWSCloudTrail client = AWSCloudTrailClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // AWS Logs
    public AWSLogs logClient() {
        return logClient(this.defaultProvider.get());
    }

    public AWSLogs logClient(String region) {
        return logClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AWSLogs logClient(AWSCredentialsProvider provider) {
        return logClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSLogs logClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {

        AWSLogs client = AWSLogsClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // CloudFormation
    public AmazonCloudFormation cloudFormationClient() {
        return cloudFormationClient(this.defaultProvider.get());
    }

    public AmazonCloudFormation cloudFormationClient(String region) {
        return cloudFormationClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AmazonCloudFormation cloudFormationClient(AWSCredentialsProvider provider) {
        return cloudFormationClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonCloudFormation cloudFormationClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonCloudFormation client = AmazonCloudFormationClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // CodeDeploy
    public AmazonCodeDeploy codeDeployClient() {
        return codeDeployClient(this.defaultProvider.get());
    }

    public AmazonCodeDeploy codeDeployClient(String region) {
        return codeDeployClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AmazonCodeDeploy codeDeployClient(AWSCredentialsProvider provider) {
        return codeDeployClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonCodeDeploy codeDeployClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonCodeDeploy client = AmazonCodeDeployClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // CloudWatch
    public AmazonCloudWatch cloudWatchClient() {
        return cloudWatchClient(this.defaultProvider.get());
    }

    public AmazonCloudWatch cloudWatchClient(String region) {
        return cloudWatchClient(this.awsConfig, this.defaultProvider.get(), region);
    }


    public AmazonCloudWatch cloudWatchClient(AWSCredentialsProvider provider) {
        return cloudWatchClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonCloudWatch cloudWatchClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonCloudWatch client = AmazonCloudWatchClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // EC2
    public AmazonEC2 ec2Client() {
        return ec2Client(this.defaultProvider.get());
    }

    public AmazonEC2 ec2Client(String region) {

        return ec2Client(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonEC2 ec2Client(AWSCredentialsProvider provider) {

        return ec2Client(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonEC2 ec2Client(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        //logger.debug("accessKey : " + provider.getCredentials().getAWSAccessKeyId());
        //logger.debug("region : " + this.awsConfig.getRegion());
        AmazonEC2 client = AmazonEC2Client.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // AWS ECS (Amazon Elastic Container Service)
    public AmazonECS ecsClient() {
        return ecsClient(this.defaultProvider.get());
    }

    public AmazonECS ecsClient(String region) {

        return ecsClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonECS ecsClient(AWSCredentialsProvider provider) {

        return ecsClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonECS ecsClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonECS client = AmazonECSClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // AWS ECR
    public AmazonECR ecrClient() {
        return ecrClient(this.defaultProvider.get());
    }

    public AmazonECR ecrClient(String region) {

        return ecrClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonECR ecrClient(AWSCredentialsProvider provider) {

        return ecrClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonECR ecrClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonECR client = AmazonECRClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // S3
    public AmazonS3 s3Client() {
        return s3Client(this.defaultProvider.get());
    }

    public AmazonS3 s3Client(String region) {

        return s3Client(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonS3 s3Client(AWSCredentialsProvider provider) {

        return s3Client(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonS3 s3Client(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonS3 client = AmazonS3Client.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // Glacier
    public AmazonGlacier glacierClient() {
        return glacierClient(this.defaultProvider.get());
    }

    public AmazonGlacier glacierClient(String region) {

        return glacierClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonGlacier glacierClient(AWSCredentialsProvider provider) {

        return glacierClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonGlacier glacierClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonGlacier client = AmazonGlacierClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // ELB ( Classic Load Balancer )
    public com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing elbClient() {
        return elbClient(this.defaultProvider.get());
    }

    public com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing elbClient(String region) {

        return elbClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing elbClient(
            AWSCredentialsProvider provider
    ) {

        return elbClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing elbClient(AwsConfiguration config) {
        AWSCredentialsProvider provider = this.defaultProvider.get();
        return elbClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing elbClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing client = com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // ELB v2 ( Application Load Balancer, Network Load Balancer )
    public AmazonElasticLoadBalancing elbV2Client() {
        return elbV2Client(this.defaultProvider.get());
    }

    public AmazonElasticLoadBalancing elbV2Client(String region) {

        return elbV2Client(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonElasticLoadBalancing elbV2Client(
            AWSCredentialsProvider provider
    ) {

        return elbV2Client(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonElasticLoadBalancing elbV2Client(AwsConfiguration config) {
        AWSCredentialsProvider provider = this.defaultProvider.get();
        return elbV2Client(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonElasticLoadBalancing elbV2Client(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonElasticLoadBalancing client = AmazonElasticLoadBalancingClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // AutoScaling
    public AmazonAutoScaling autoScalingClient() {
        return autoScalingClient(this.defaultProvider.get());
    }

    public AmazonAutoScaling autoScalingClient(String region) {
        return autoScalingClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonAutoScaling autoScalingClient(AWSCredentialsProvider provider) {
        return autoScalingClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonAutoScaling autoScalingClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonAutoScaling client = AmazonAutoScalingClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // RDS
    public AmazonRDS rdsClient() {
        return rdsClient(this.defaultProvider.get());
    }

    public AmazonRDS rdsClient(String region) {
        return rdsClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonRDS rdsClient(AWSCredentialsProvider provider) {
        return rdsClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonRDS rdsClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonRDS client = AmazonRDSClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // DynamoDB
    public AmazonDynamoDB dynamoDBClient() {
        return dynamoDBClient(this.defaultProvider.get());
    }

    public AmazonDynamoDB dynamoDBClient(String region) {

        return dynamoDBClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonDynamoDB dynamoDBClient(AWSCredentialsProvider provider) {

        return dynamoDBClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonDynamoDB dynamoDBClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonDynamoDB client = AmazonDynamoDBClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // ElastiCache
    public AmazonElastiCache elastiCacheClient() {
        return elastiCacheClient(this.defaultProvider.get());
    }

    public AmazonElastiCache elastiCacheClient(String region) {
        return elastiCacheClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonElastiCache elastiCacheClient(AWSCredentialsProvider provider) {
        return elastiCacheClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonElastiCache elastiCacheClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonElastiCache client = AmazonElastiCacheClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // Redshift
    public AmazonRedshift redshiftClient() {
        return redshiftClient(this.defaultProvider.get());
    }

    public AmazonRedshift redshiftClient(String region) {
        return redshiftClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonRedshift redshiftClient(AWSCredentialsProvider provider) {
        return redshiftClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonRedshift redshiftClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonRedshift client = AmazonRedshiftClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // Lambda
    public AWSLambda lambdaClient() {
        return lambdaClient(this.defaultProvider.get());
    }

    public AWSLambda lambdaClient(String region) {
        return lambdaClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSLambda lambdaClient(AWSCredentialsProvider provider) {
        return lambdaClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSLambda lambdaClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AWSLambda client = AWSLambdaClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // Elastic Beanstalk
    public AWSElasticBeanstalk beanstalkClient() {
        return beanstalkClient(this.defaultProvider.get());
    }

    public AWSElasticBeanstalk beanstalkClient(String region) {
        return beanstalkClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSElasticBeanstalk beanstalkClient(AWSCredentialsProvider provider) {
        return beanstalkClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSElasticBeanstalk beanstalkClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AWSElasticBeanstalk client = AWSElasticBeanstalkClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // Cloud Front
    public AmazonCloudFront cloudFrontClient() {
        return cloudFrontClient(this.defaultProvider.get());
    }

    public AmazonCloudFront cloudFrontClient(String region) {
        return cloudFrontClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonCloudFront cloudFrontClient(AWSCredentialsProvider provider) {
        return cloudFrontClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonCloudFront cloudFrontClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonCloudFront client = AmazonCloudFrontClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // ROUTE53
    public AmazonRoute53 route53Client() {
        return route53Client(this.defaultProvider.get());
    }

    public AmazonRoute53 route53Client(String region) {
        return route53Client(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonRoute53 route53Client(AWSCredentialsProvider provider) {
        return route53Client(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonRoute53 route53Client(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonRoute53 client = AmazonRoute53Client.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }


    // API Gateway
    public AmazonApiGateway apiGatewayClient() {
        return apiGatewayClient(this.defaultProvider.get());
    }

    public AmazonApiGateway apiGatewayClient(String region) {
        return apiGatewayClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonApiGateway apiGatewayClient(AWSCredentialsProvider provider) {
        return apiGatewayClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonApiGateway apiGatewayClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonApiGateway client = AmazonApiGatewayClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // Athena
    public AmazonAthena athenaClient() {
        return athenaClient(this.defaultProvider.get());
    }

    public AmazonAthena athenaClient(String region) {
        return athenaClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonAthena athenaClient(AWSCredentialsProvider provider) {
        return athenaClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonAthena athenaClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonAthena client = AmazonAthenaClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // Elastic Search
    public AWSElasticsearch elasticsearchClient() {
        return elasticsearchClient(this.defaultProvider.get());
    }

    public AWSElasticsearch elasticsearchClient(String region) {
        return elasticsearchClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSElasticsearch elasticsearchClient(AWSCredentialsProvider provider) {
        return elasticsearchClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSElasticsearch elasticsearchClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AWSElasticsearch client = AWSElasticsearchClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // Elastic MapReduce
    public AmazonElasticMapReduce elasticMapReduceClient() {
        return elasticMapReduceClient(this.defaultProvider.get());
    }

    public AmazonElasticMapReduce elasticMapReduceClient(String region) {
        return elasticMapReduceClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonElasticMapReduce elasticMapReduceClient(AWSCredentialsProvider provider) {
        return elasticMapReduceClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonElasticMapReduce elasticMapReduceClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonElasticMapReduce client = AmazonElasticMapReduceClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // Kinesis
    public AmazonKinesis kinesisClient() {
        return kinesisClient(this.defaultProvider.get());
    }

    public AmazonKinesis kinesisClient(String region) {
        return kinesisClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonKinesis kinesisClient(AWSCredentialsProvider provider) {
        return kinesisClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonKinesis kinesisClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonKinesis client = AmazonKinesisClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // AWS IoT
    public AWSIot iotClient() {
        return iotClient(this.defaultProvider.get());
    }

    public AWSIot iotClient(String region) {
        return iotClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSIot iotClient(AWSCredentialsProvider provider) {
        return iotClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSIot iotClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AWSIot client = AWSIotClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // WAF
    public AWSWAF wafClient() {
        return wafClient(this.defaultProvider.get());
    }

    public AWSWAF wafClient(String region) {
        return wafClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AWSWAF wafClient(AWSCredentialsProvider provider) {
        return wafClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AWSWAF wafClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AWSWAF client = AWSWAFClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // SES (Simple Email Service)
    public AmazonSimpleEmailService simpleEmailServiceClient() {
        return simpleEmailServiceClient(this.defaultProvider.get());
    }

    public AmazonSimpleEmailService simpleEmailServiceClient(String region) {
        return simpleEmailServiceClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonSimpleEmailService simpleEmailServiceClient(AWSCredentialsProvider provider) {
        return simpleEmailServiceClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonSimpleEmailService simpleEmailServiceClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // SQS
    public AmazonSQS sqsClient() {
        return sqsClient(this.defaultProvider.get());
    }

    public AmazonSQS sqsClient(String region) {

        return sqsClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonSQS sqsClient(AWSCredentialsProvider provider) {

        return sqsClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonSQS sqsClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonSQS client = AmazonSQSClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();

        return client;
    }

    // SNS
    public AmazonSNS snsClient() {
        return snsClient(this.defaultProvider.get());
    }

    public AmazonSNS snsClient(String region) {
        return snsClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonSNS snsClient(AWSCredentialsProvider provider) {
        return snsClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonSNS snsClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonSNS client = AmazonSNSClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }

    // SimpleWorkflow
    public AmazonSimpleWorkflow swfClient() {
        return swfClient(this.defaultProvider.get());
    }

    public AmazonSimpleWorkflow swfClient(String region) {
        return swfClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonSimpleWorkflow swfClient(AWSCredentialsProvider provider) {
        return swfClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonSimpleWorkflow swfClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonSimpleWorkflow client = AmazonSimpleWorkflowClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


    // SimpleEmailService
    public AmazonSimpleEmailService sesClient() {
        return sesClient(this.defaultProvider.get());
    }

    public AmazonSimpleEmailService sesClient(String region) {
        return sesClient(this.awsConfig, this.defaultProvider.get(), region);
    }

    public AmazonSimpleEmailService sesClient(AWSCredentialsProvider provider) {
        return sesClient(this.awsConfig, provider, this.awsConfig.getRegion());
    }

    public AmazonSimpleEmailService sesClient(
            AwsConfiguration config
            , AWSCredentialsProvider provider
            , String region
    ) {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClient.builder()
                .withCredentials(provider)
                .withClientConfiguration(clientConfig(config))
                .withRegion(region)
                .build();
        return client;
    }


}