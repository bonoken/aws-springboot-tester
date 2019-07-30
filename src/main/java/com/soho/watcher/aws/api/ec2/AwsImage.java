package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.DeregisterImageRequest;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.ModifyImageAttributeRequest;
import com.soho.watcher.aws.api.AwsException;


public class AwsImage {

    private static final Logger logger = LoggerFactory.getLogger(AwsImage.class);

    public AwsImage(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public String createImage(CreateImageRequest request, Map<String, String> tags) {
        try {
            CreateImageResult result = ec2.createImage(request);
            // set tags
            if (result != null) {
                String imageId = result.getImageId();

                tagHandler.setTags(imageId, tags);
                return imageId;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deregisterImage(String imageId) {
        boolean rtn = false;
        try {

            DeregisterImageRequest request = new DeregisterImageRequest();

            request.setImageId(imageId);

            ec2.deregisterImage(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public List<Image> listImagesInSelf(DescribeImagesRequest request) {

        List<Image> lists = new ArrayList<Image>();

        try {

            request.withOwners("self");
            DescribeImagesResult result = ec2.describeImages(request);


            if (null != result) {
                lists = result.getImages();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Image> listImagesInSelf() {

        List<Image> lists = new ArrayList<Image>();

        try {

            DescribeImagesRequest request = new DescribeImagesRequest();
            request.withOwners("self");
            DescribeImagesResult result = ec2.describeImages(request);

            if (null != result) {
                lists = result.getImages();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Image> listImagesInSelfByInstanceId(String instanceId) {

        List<Image> lists = new ArrayList<Image>();

        try {
            DescribeImagesRequest request = new DescribeImagesRequest();
            request.withOwners("self");
            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("attachment.instance-id").withValues(instanceId));
            request.withFilters(filters);

            DescribeImagesResult result = ec2.describeImages(request);


            if (null != result) {
                lists = result.getImages();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public Image selectImageInSelf(String imageId) {
        try {

            DescribeImagesRequest request = new DescribeImagesRequest();
            request.withOwners("self");

            if (null != imageId) {
                request.withImageIds(imageId);
				/*				Collection<String> imageIds = new Vector<String>();
				imageIds.add(imageId);
				request.setImageIds(imageIds);*/
                DescribeImagesResult result = ec2.describeImages(request);
                if (null != result) {
                    if (result.getImages().size() > 0) {
                        return result.getImages().get(0);
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


    public boolean modifyImageAttribute(ModifyImageAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            //ModifyImageAttributeResult result = ec2.modifyImageAttribute(request);
            ec2.modifyImageAttribute(request);

            // set tags
            tagHandler.setTags(request.getImageId(), tags);

            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}