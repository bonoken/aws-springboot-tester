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
import com.amazonaws.services.ec2.model.AttachVolumeRequest;
import com.amazonaws.services.ec2.model.AttachVolumeResult;
import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.CreateVolumeResult;
import com.amazonaws.services.ec2.model.DeleteVolumeRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.DetachVolumeRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.ModifyVolumeAttributeRequest;
import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.ec2.model.VolumeAttachment;
import com.soho.watcher.aws.api.AwsException;

public class AwsVolume {

    private static final Logger logger = LoggerFactory.getLogger(AwsInstance.class);

    public AwsVolume(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public Volume createVolume(CreateVolumeRequest request, Map<String, String> tags) {
        try {
            //CreateVolumeRequest request = new CreateVolumeRequest();
            CreateVolumeResult result = ec2.createVolume(request);
            // set tags
            if (result != null) {
                Volume volume = result.getVolume();
                tagHandler.setTags(volume.getVolumeId(), tags);
                return volume;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteVolume(String volumeId) {

        try {

            DeleteVolumeRequest request = new DeleteVolumeRequest();

            request.setVolumeId(volumeId);

            ec2.deleteVolume(request);

            return true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }


    public List<Volume> listVolumes() {
        List<Volume> lists = new ArrayList<Volume>();
        try {
            //DescribeVolumesRequest request = new DescribeVolumesRequest();
            DescribeVolumesResult result = ec2.describeVolumes();
            if (null != result) {
                lists = result.getVolumes();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Volume> listVolumes(DescribeVolumesRequest request) {
        List<Volume> lists = new ArrayList<Volume>();
        try {
            DescribeVolumesResult result = ec2.describeVolumes(request);

            if (null != result) {
                lists = result.getVolumes();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Volume> listVolumesByInstanceId(String instanceId) {
        List<Volume> lists = new ArrayList<Volume>();
        try {
            DescribeVolumesRequest request = new DescribeVolumesRequest();

            List<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("attachment.instance-id").withValues(instanceId));
            request.withFilters(filters);

            return this.listVolumes(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public Volume selectVolume(String volumeId) {
        try {
            DescribeVolumesRequest request = new DescribeVolumesRequest();

            if (null != volumeId) {
                request.withVolumeIds(volumeId);
				/*				Collection<String> volumeIds = new Vector<String>();
				volumeIds.add(volumeId);
				request.setVolumeIds(volumeIds);*/
                DescribeVolumesResult result = ec2.describeVolumes(request);

                if (null != result) {
                    if (result.getVolumes().size() > 0) {
                        return result.getVolumes().get(0);
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

    public boolean modifyVolumeAttribute(ModifyVolumeAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            //ModifyVolumeAttributeRequest request = new ModifyVolumeAttributeRequest();
            ec2.modifyVolumeAttribute(request);
            tagHandler.setTags(request.getVolumeId(), tags);
            rtn = true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


    public VolumeAttachment attachVolume(AttachVolumeRequest request) {
        try {
            //AttachVolumeRequest request = new AttachVolumeRequest();

            AttachVolumeResult result = ec2.attachVolume(request);
            if (null != result) {
                return result.getAttachment();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean detachVolume(String volumeId, boolean force) {
        try {
            DetachVolumeRequest request = new DetachVolumeRequest();

            request.setVolumeId(volumeId);
            request.setForce(force);
            ec2.detachVolume(request);
            return true;
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return false;
    }


}