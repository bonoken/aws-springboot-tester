package com.soho.watcher.aws.api.s3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.soho.watcher.aws.api.AwsException;



public class AwsS3 {

	private static final Logger logger = LoggerFactory.getLogger(AwsS3.class);


	public AwsS3(AmazonS3 s3) {
		this.s3 = s3;
	}

	private AmazonS3 s3;


	// container function
	public Bucket createBucket(String bucketName) {
		try {
			Bucket bucket = s3.createBucket(bucketName);

			return bucket;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return null;
	}

	public boolean deleteBucket(String bucketName) {
		try {
			s3.deleteBucket(bucketName);

			return true;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return false;
	}

	public List<Bucket> listBuckets() {
		List<Bucket> lists = new ArrayList<Bucket>();
		try {
			if(null != s3.listBuckets()) {
				lists = s3.listBuckets();				
			}

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return lists;
	}


	// object function
	public boolean deleteObject(String bucketName, String key) {
		boolean rtn = false;
		try {
			s3.deleteObject(bucketName, key);

			rtn = true;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return rtn;
	}

	public List<S3ObjectSummary> listObjects(ListObjectsRequest request) {
		List<S3ObjectSummary> lists = new ArrayList<S3ObjectSummary>();
		try {
			ObjectListing objectListing = s3.listObjects(request);
			lists = objectListing.getObjectSummaries();

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}

		return lists;
	}


	public List<S3ObjectSummary> listObjects(String bucketName, String prefix) {
		List<S3ObjectSummary> lists = new ArrayList<S3ObjectSummary>();
		try {

			if(null == bucketName || "".equals(bucketName)){
				return null;
			};

			ListObjectsRequest request = new ListObjectsRequest();
			request.setBucketName(bucketName);

			if(null != prefix && !"".equals(prefix)) {
				request.setPrefix(prefix);			
			}

			return listObjects(request);

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return lists;

	}




	public PutObjectResult putObject(PutObjectRequest request) {

		try {
			PutObjectResult result = s3.putObject(request);

			return result;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return null;
	}


	public S3Object getObject(GetObjectRequest request) {

		try {
			S3Object result = s3.getObject(request);

			return result;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return null;
	}

	public S3ObjectInputStream getObjectContent(GetObjectRequest request) {

		try {
			S3Object object = s3.getObject(request);
			S3ObjectInputStream result = object.getObjectContent();

			return result;

		} catch (AmazonServiceException ase) {
			AwsException.printLog(logger, ase);
		} catch (AmazonClientException ace) {
			AwsException.printLog(logger, ace);
		}
		return null;
	}

	private void displayTextInputStream(InputStream input) {

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while (true) {
				String line = reader.readLine();
				if (line == null) break;

				logger.error("    " + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}


}
