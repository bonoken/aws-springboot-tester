package com.soho.watcher.aws.api.ec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DeleteTagsRequest;
import com.amazonaws.services.ec2.model.DescribeTagsRequest;
import com.amazonaws.services.ec2.model.DescribeTagsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TagDescription;
import com.soho.watcher.aws.api.AwsException;

public class AwsEC2TagsHandler {

    private static final Logger logger = LoggerFactory.getLogger(AwsEC2TagsHandler.class);

    public AwsEC2TagsHandler(AmazonEC2 ec2) {
        this.ec2 = ec2;
    }

    private AmazonEC2 ec2;

    private Map<String, String> putTags;

    private Map<String, String> removeTags;

    // function
    public boolean setTags(String resourceId, Map<String, String> tags) {
        boolean rtn = false;
        try {
            if (null == resourceId || "".equals(resourceId)) {
                return rtn;
            }
            if (null == tags || tags.size() < 1) {
                return rtn;
            }

            DescribeTagsRequest rReq = new DescribeTagsRequest();
            Vector<Filter> filters = new Vector<Filter>();

            filters.add(new Filter("resource-id").withValues(resourceId));

            rReq.setFilters(filters);

            DescribeTagsResult result = ec2.describeTags(rReq);

            Map<String, String> oldTags = new HashMap<String, String>();
            if (result != null) {
                if (result.getTags().size() > 0) {
                    oldTags = convertTagsDescriptionToMaps(result.getTags());
                }
            }

            this.makeTagList(tags, oldTags);

            if (this.removeTags.size() > 0) {

                DeleteTagsRequest dReq = new DeleteTagsRequest();
                dReq.withResources(resourceId);
                dReq.setTags(convertMapsToTags(this.removeTags));
                ec2.deleteTags(dReq);
            }

            if (this.putTags.size() > 0) {
                CreateTagsRequest cReq = new CreateTagsRequest();
                cReq.withResources(resourceId);
                cReq.setTags(convertMapsToTags(this.putTags));
                ec2.createTags(cReq);
            }
        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }
        return rtn;
    }

    private Map<String, String> convertTagsDescriptionToMaps(List<TagDescription> tags) {
        Map<String, String> rtnTagMap = new HashMap<String, String>();

        for (TagDescription tag : tags) {
            rtnTagMap.put(tag.getKey(), tag.getKey());
        }
        return rtnTagMap;
    }

    private List<Tag> convertMapsToTags(Map<String, String> tags) {
        List<Tag> rtnTagMap = new ArrayList<Tag>();

        for (String key : tags.keySet()) {
            rtnTagMap.add(new Tag(key, tags.get(key)));
        }
        return rtnTagMap;
    }

    // 입력/삭제 tag list 작성
    private void makeTagList(Map<String, String> newTagMaps, Map<String, String> oldTagMaps) {

        if (null == newTagMaps || newTagMaps.size() == 0) {
            this.putTags = new HashMap<String, String>();
        }

        newTagMaps = this.filterTags(newTagMaps);

        if (null == oldTagMaps || oldTagMaps.size() == 0) {
            if (newTagMaps.size() > 0) {
                this.putTags = newTagMaps;
            }
        } else {
            Map<String, String> rtnPutTags = new HashMap<String, String>();
            Map<String, String> rtnRemoveTags = new HashMap<String, String>();

            for (String nkey : newTagMaps.keySet()) {
                rtnPutTags.put(nkey, newTagMaps.get(nkey));
                for (String okey : oldTagMaps.keySet()) {
                    if (nkey.equalsIgnoreCase(okey)) {
                        rtnRemoveTags.put(okey, oldTagMaps.get(nkey));
                    }
                }
            }
            this.putTags = rtnPutTags;
            this.removeTags = rtnRemoveTags;
        }
    }

    // null처리
    private Map<String, String> filterTags(Map<String, String> tags) {
        Map<String, String> rtnTags = new HashMap<String, String>();
        for (String key : tags.keySet()) {
            if (null == key || "".equals(key) || null == tags.get(key) || "".equals(tags.get(key))) {
            } else {
                rtnTags.put(key, tags.get(key));
            }
        }
        return rtnTags;
    }


}
