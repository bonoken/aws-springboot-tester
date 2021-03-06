package com.soho.watcher.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soho.watcher.aws.api.AwsConfiguration;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MavenLibToStringConvertor {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(MavenLibToStringConvertor.class);
	
	
	@Test
	public void contextLoads()  {
		
		String[] list = {
				"aopalliance-1.0.jar"
				,"aws-java-sdk-1.11.601.jar"
				,"aws-java-sdk-acm-1.11.601.jar"
				,"aws-java-sdk-alexaforbusiness-1.11.601.jar"
				,"aws-java-sdk-api-gateway-1.11.601.jar"
				,"aws-java-sdk-applicationautoscaling-1.11.601.jar"
				,"aws-java-sdk-appstream-1.11.601.jar"
				,"aws-java-sdk-appsync-1.11.601.jar"
				,"aws-java-sdk-athena-1.11.601.jar"
				,"aws-java-sdk-autoscaling-1.11.601.jar"
				,"aws-java-sdk-autoscalingplans-1.11.601.jar"
				,"aws-java-sdk-batch-1.11.601.jar"
				,"aws-java-sdk-budgets-1.11.601.jar"
				,"aws-java-sdk-cloud9-1.11.601.jar"
				,"aws-java-sdk-clouddirectory-1.11.601.jar"
				,"aws-java-sdk-cloudformation-1.11.601.jar"
				,"aws-java-sdk-cloudfront-1.11.601.jar"
				,"aws-java-sdk-cloudhsm-1.11.601.jar"
				,"aws-java-sdk-cloudhsmv2-1.11.601.jar"
				,"aws-java-sdk-cloudsearch-1.11.601.jar"
				,"aws-java-sdk-cloudtrail-1.11.601.jar"
				,"aws-java-sdk-cloudwatch-1.11.601.jar"
				,"aws-java-sdk-cloudwatchmetrics-1.11.601.jar"
				,"aws-java-sdk-codebuild-1.11.601.jar"
				,"aws-java-sdk-codecommit-1.11.601.jar"
				,"aws-java-sdk-codedeploy-1.11.601.jar"
				,"aws-java-sdk-codepipeline-1.11.601.jar"
				,"aws-java-sdk-codestar-1.11.601.jar"
				,"aws-java-sdk-cognitoidentity-1.11.601.jar"
				,"aws-java-sdk-cognitoidp-1.11.601.jar"
				,"aws-java-sdk-cognitosync-1.11.601.jar"
				,"aws-java-sdk-comprehend-1.11.601.jar"
				,"aws-java-sdk-config-1.11.601.jar"
				,"aws-java-sdk-core-1.11.601.jar"
				,"aws-java-sdk-costandusagereport-1.11.601.jar"
				,"aws-java-sdk-costexplorer-1.11.601.jar"
				,"aws-java-sdk-datapipeline-1.11.601.jar"
				,"aws-java-sdk-dax-1.11.601.jar"
				,"aws-java-sdk-devicefarm-1.11.601.jar"
				,"aws-java-sdk-directconnect-1.11.601.jar"
				,"aws-java-sdk-directory-1.11.601.jar"
				,"aws-java-sdk-discovery-1.11.601.jar"
				,"aws-java-sdk-dms-1.11.601.jar"
				,"aws-java-sdk-dynamodb-1.11.601.jar"
				,"aws-java-sdk-ec2-1.11.601.jar"
				,"aws-java-sdk-ecr-1.11.601.jar"
				,"aws-java-sdk-ecs-1.11.601.jar"
				,"aws-java-sdk-efs-1.11.601.jar"
				,"aws-java-sdk-elasticache-1.11.601.jar"
				,"aws-java-sdk-elasticbeanstalk-1.11.601.jar"
				,"aws-java-sdk-elasticloadbalancing-1.11.601.jar"
				,"aws-java-sdk-elasticloadbalancingv2-1.11.601.jar"
				,"aws-java-sdk-elasticsearch-1.11.601.jar"
				,"aws-java-sdk-elastictranscoder-1.11.601.jar"
				,"aws-java-sdk-emr-1.11.601.jar"
				,"aws-java-sdk-events-1.11.601.jar"
				,"aws-java-sdk-gamelift-1.11.601.jar"
				,"aws-java-sdk-glacier-1.11.601.jar"
				,"aws-java-sdk-glue-1.11.601.jar"
				,"aws-java-sdk-greengrass-1.11.601.jar"
				,"aws-java-sdk-guardduty-1.11.601.jar"
				,"aws-java-sdk-health-1.11.601.jar"
				,"aws-java-sdk-iam-1.11.601.jar"
				,"aws-java-sdk-importexport-1.11.601.jar"
				,"aws-java-sdk-inspector-1.11.601.jar"
				,"aws-java-sdk-iot-1.11.601.jar"
				,"aws-java-sdk-iotjobsdataplane-1.11.601.jar"
				,"aws-java-sdk-kinesis-1.11.601.jar"
				,"aws-java-sdk-kinesisvideo-1.11.601.jar"
				,"aws-java-sdk-kms-1.11.601.jar"
				,"aws-java-sdk-lambda-1.11.601.jar"
				,"aws-java-sdk-lex-1.11.601.jar"
				,"aws-java-sdk-lexmodelbuilding-1.11.601.jar"
				,"aws-java-sdk-lightsail-1.11.601.jar"
				,"aws-java-sdk-logs-1.11.601.jar"
				,"aws-java-sdk-machinelearning-1.11.601.jar"
				,"aws-java-sdk-marketplacecommerceanalytics-1.11.601.jar"
				,"aws-java-sdk-marketplaceentitlement-1.11.601.jar"
				,"aws-java-sdk-marketplacemeteringservice-1.11.601.jar"
				,"aws-java-sdk-mechanicalturkrequester-1.11.601.jar"
				,"aws-java-sdk-mediaconvert-1.11.601.jar"
				,"aws-java-sdk-medialive-1.11.601.jar"
				,"aws-java-sdk-mediapackage-1.11.601.jar"
				,"aws-java-sdk-mediastore-1.11.601.jar"
				,"aws-java-sdk-mediastoredata-1.11.601.jar"
				,"aws-java-sdk-migrationhub-1.11.601.jar"
				,"aws-java-sdk-mobile-1.11.601.jar"
				,"aws-java-sdk-models-1.11.601.jar"
				,"aws-java-sdk-mq-1.11.601.jar"
				,"aws-java-sdk-opsworks-1.11.601.jar"
				,"aws-java-sdk-opsworkscm-1.11.601.jar"
				,"aws-java-sdk-organizations-1.11.601.jar"
				,"aws-java-sdk-pinpoint-1.11.601.jar"
				,"aws-java-sdk-polly-1.11.601.jar"
				,"aws-java-sdk-pricing-1.11.601.jar"
				,"aws-java-sdk-rds-1.11.601.jar"
				,"aws-java-sdk-redshift-1.11.601.jar"
				,"aws-java-sdk-rekognition-1.11.601.jar"
				,"aws-java-sdk-resourcegroups-1.11.601.jar"
				,"aws-java-sdk-resourcegroupstaggingapi-1.11.601.jar"
				,"aws-java-sdk-route53-1.11.601.jar"
				,"aws-java-sdk-s3-1.11.601.jar"
				,"aws-java-sdk-sagemaker-1.11.601.jar"
				,"aws-java-sdk-sagemakerruntime-1.11.601.jar"
				,"aws-java-sdk-serverlessapplicationrepository-1.11.601.jar"
				,"aws-java-sdk-servermigration-1.11.601.jar"
				,"aws-java-sdk-servicecatalog-1.11.601.jar"
				,"aws-java-sdk-servicediscovery-1.11.601.jar"
				,"aws-java-sdk-ses-1.11.601.jar"
				,"aws-java-sdk-shield-1.11.601.jar"
				,"aws-java-sdk-simpledb-1.11.601.jar"
				,"aws-java-sdk-simpleworkflow-1.11.601.jar"
				,"aws-java-sdk-snowball-1.11.601.jar"
				,"aws-java-sdk-sns-1.11.601.jar"
				,"aws-java-sdk-sqs-1.11.601.jar"
				,"aws-java-sdk-ssm-1.11.601.jar"
				,"aws-java-sdk-stepfunctions-1.11.601.jar"
				,"aws-java-sdk-storagegateway-1.11.601.jar"
				,"aws-java-sdk-sts-1.11.601.jar"
				,"aws-java-sdk-support-1.11.601.jar"
				,"aws-java-sdk-swf-libraries-1.11.22.jar"
				,"aws-java-sdk-transcribe-1.11.601.jar"
				,"aws-java-sdk-translate-1.11.601.jar"
				,"aws-java-sdk-waf-1.11.601.jar"
				,"aws-java-sdk-workdocs-1.11.601.jar"
				,"aws-java-sdk-workmail-1.11.601.jar"
				,"aws-java-sdk-workspaces-1.11.601.jar"
				,"aws-java-sdk-xray-1.11.601.jar"
				,"bcprov-jdk16-1.45.jar"
				,"c3p0-0.9.1.1.jar"
				,"cloning-1.9.0.jar"
				,"commons-beanutils-1.7.0.jar"
				,"commons-beanutils-core-1.8.0.jar"
				,"commons-codec-1.9.jar"
				,"commons-collections-3.2.2.jar"
				,"commons-configuration-1.6.jar"
				,"commons-csv-1.1.jar"
				,"commons-digester-1.8.jar"
				,"commons-io-2.4.jar"
				,"commons-lang-2.4.jar"
				,"commons-lang3-3.3.2.jar"
				,"commons-logging-1.1.1.jar"
				,"concurrentlinkedhashmap-lru-1.4.1.jar"
				,"fastutil-7.0.10.jar"
				,"gson-2.3.1.jar"
				,"guava-18.0.jar"
				,"guice-3.0.jar"
				,"hibernate-jpa-2.0-api-1.0.0.Final.jar"
				,"httpclient-4.5.2.jar"
				,"httpcore-4.4.4.jar"
				,"ion-java-1.0.2.jar"
				,"jackson-annotations-2.6.3.jar"
				,"jackson-core-2.6.3.jar"
				,"jackson-databind-2.6.3.jar"
				,"jackson-dataformat-cbor-2.6.7.jar"
				,"jackson-datatype-json-org-2.6.3.jar"
				,"jackson-module-afterburner-2.6.3.jar"
				,"jackson-module-jaxb-annotations-2.6.3.jar"
				,"javassist-3.16.1-GA.jar"
				,"javax.inject-1.jar"
				,"jcl-over-slf4j-1.7.12.jar"
				,"jettison-1.3.3.jar"
				,"jmespath-java-1.11.601.jar"
				,"jna-4.0.0.jar"
				,"jna-platform-4.0.0.jar"
				,"joda-time-2.3.jar"
				,"json-20090211_1.jar"
				,"json-simple-1.1.jar"
				,"jul-to-slf4j-1.7.12.jar"
				,"junit-3.8.1.jar"
				,"log4j-1.2-api-2.2.jar"
				,"log4j-1.2.17.jar"
				,"log4j-api-2.2.jar"
				,"log4j-core-2.2.jar"
				,"log4j-slf4j-impl-2.2.jar"
				,"mapdb-1.0.7.jar"
				,"netty-buffer-4.1.17.Final.jar"
				,"netty-codec-4.1.17.Final.jar"
				,"netty-codec-http-4.1.17.Final.jar"
				,"netty-common-4.1.17.Final.jar"
				,"netty-handler-4.1.17.Final.jar"
				,"netty-resolver-4.1.17.Final.jar"
				,"netty-transport-4.1.17.Final.jar"
				,"nimconfigxml-1.jar"
				,"nimlookup-1.0.jar"
				,"nimsecurity-1.1.0.jar"
				,"objenesis-1.2.jar"
				,"opencsv-2.3.jar"
				,"orientdb-core-2.1.8.jar"
				,"orientdb-object-2.1.8.jar"
				,"probe-api-2.5.0.jar"
				,"quartz-2.2.1.jar"
				,"slf4j-api-1.6.2.jar"
				,"slf4j-simple-1.6.2.jar"
				,"snappy-java-1.1.0.1.jar"
				,"stax-api-1.0.1.jar"
				,"zip4j-1.3.2.jar"
		};
		
		printLibList(list);
	}
	
	public void printLibList(String[] list) {
		StringBuffer libLine = new StringBuffer();
		for(String lib : list) {
			if(null != libLine && libLine.length() > 0){
				libLine.append(" lib/"+lib);
			} else {
				libLine.append(". lib/" +lib);
			}
		}
		System.out.println(libLine);
	}
	
	

	

}
