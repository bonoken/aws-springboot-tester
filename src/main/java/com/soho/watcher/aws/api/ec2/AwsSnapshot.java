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
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.ModifySnapshotAttributeRequest;
import com.amazonaws.services.ec2.model.Snapshot;
import com.soho.watcher.aws.api.AwsException;


public class AwsSnapshot {

    private static final Logger logger = LoggerFactory.getLogger(AwsSnapshot.class);

    public AwsSnapshot(AmazonEC2 ec2) {
        this.ec2 = ec2;
        this.tagHandler = new AwsEC2TagsHandler(ec2);
    }

    private AmazonEC2 ec2;

    private AwsEC2TagsHandler tagHandler;

    //  function
    public Snapshot createSnapshot(CreateSnapshotRequest request, Map<String, String> tags) {
        try {
            //CreateSnapshotRequest request = new CreateSnapshotRequest();

            CreateSnapshotResult result = ec2.createSnapshot(request);

            // set tags
            if (result != null) {
                Snapshot snapshot = result.getSnapshot();

                tagHandler.setTags(snapshot.getSnapshotId(), tags);
                return snapshot;
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return null;
    }


    public boolean deleteSnapshot(String snapshotId) {
        boolean rtn = false;
        try {

            DeleteSnapshotRequest request = new DeleteSnapshotRequest();

            request.setSnapshotId(snapshotId);

            ec2.deleteSnapshot(request);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    public List<Snapshot> listSnapshotsInSelf() {
        List<Snapshot> lists = new ArrayList<Snapshot>();
        try {
            DescribeSnapshotsRequest request = new DescribeSnapshotsRequest();
            request.withOwnerIds("self");
            DescribeSnapshotsResult result = ec2.describeSnapshots(request);


            if (null != result) {
                lists = result.getSnapshots();
            }


        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public List<Snapshot> listSnapshotsInSelf(DescribeSnapshotsRequest request) {
        List<Snapshot> lists = new ArrayList<Snapshot>();
        try {
            request.withOwnerIds("self");
            DescribeSnapshotsResult result = ec2.describeSnapshots(request);

            if (null != result) {
                lists = result.getSnapshots();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }


    public List<Snapshot> listSnapshotsByVolumeId(String volumeId) {

        List<Snapshot> lists = new ArrayList<Snapshot>();

        try {

            DescribeSnapshotsRequest request = new DescribeSnapshotsRequest();

            request.withOwnerIds("self");
            List<Filter> filters = new Vector<Filter>();
            filters.add(new Filter("volume-id").withValues(volumeId));
            request.withFilters(filters);

            return this.listSnapshotsInSelf(request);

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return lists;
    }

    public Snapshot selectSnapshot(String snapshotId) {
        try {

            if (null != snapshotId) {
                DescribeSnapshotsRequest request = new DescribeSnapshotsRequest();

                request.withOwnerIds("self");
                request.withSnapshotIds(snapshotId);
                DescribeSnapshotsResult result = ec2.describeSnapshots(request);

                if (null != result) {
                    if (result.getSnapshots().size() > 0) {
                        return result.getSnapshots().get(0);
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

    public boolean modifySnapshotAttribute(ModifySnapshotAttributeRequest request, Map<String, String> tags) {
        boolean rtn = false;
        try {
            //ModifySnapshotAttributeRequest request = new ModifySnapshotAttributeRequest();
            ec2.modifySnapshotAttribute(request);
            // set tags
            tagHandler.setTags(request.getSnapshotId(), tags);

            rtn = true;

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }


}