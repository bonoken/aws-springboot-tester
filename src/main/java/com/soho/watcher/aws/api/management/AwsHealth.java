package com.soho.watcher.aws.api.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.health.AWSHealth;
import com.amazonaws.services.health.model.AffectedEntity;
import com.amazonaws.services.health.model.DescribeAffectedEntitiesRequest;
import com.amazonaws.services.health.model.DescribeAffectedEntitiesResult;
import com.amazonaws.services.health.model.DescribeEventsRequest;
import com.amazonaws.services.health.model.DescribeEventsResult;
import com.amazonaws.services.health.model.EntityFilter;
import com.amazonaws.services.health.model.Event;
import com.amazonaws.services.health.model.EventFilter;
import com.soho.watcher.aws.api.AwsException;

import static java.util.Collections.singletonList;


public class AwsHealth {

    private static final Logger logger = LoggerFactory.getLogger(AwsHealth.class);

    public AwsHealth(AWSHealth client) {
        this.health = client;
    }

    private AWSHealth health;


    //  function
    public List<Event> listEvents(String service, Collection<String> regions) {
        Collection<String> services = new ArrayList<String>();
        services.add(service);
        return listEvents(services, regions);
    }

    public List<Event> listEvents(Collection<String> regions) {
        return listEvents(new ArrayList<String>(), regions);
    }

    public List<Event> listEvents(Collection<String> services, Collection<String> regions) {

        EventFilter filter = new EventFilter();

        if (null != services && services.size() > 0) {
            filter.setServices(services);
        } else {
            //filter.setServices(singletonList("EC2"));
            filter.withServices("EC2");
        }

        //Collection<String> regions = new ArrayList<String>();
        //regions.add("us-west-1");
        filter.setRegions(regions);

        return listEvents(filter);
    }

    public List<Event> listEvents(EventFilter filter) {

        List<Event> lists = new ArrayList<Event>();

        try {
            DescribeEventsRequest request = new DescribeEventsRequest();

            // Filter on any field from the supported AWS Health EventFilter model.
            // Here is an example for region us-west-1 events from the EC2 service.
            //filter.setServices(singletonList("EC2");

            //EventFilter filter = new EventFilter();
            request.setFilter(filter);

            DescribeEventsResult response = health.describeEvents(request);
            if (null != response) {
                lists = response.getEvents();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<Event> listEvents() {

        List<Event> lists = new ArrayList<Event>();

        try {
            DescribeEventsRequest request = new DescribeEventsRequest();


            DescribeEventsResult response = health.describeEvents(request);
            if (null != response) {
                lists = response.getEvents();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }

    public List<AffectedEntity> listAffectedEntities(EntityFilter filter) {
        List<AffectedEntity> lists = new ArrayList<AffectedEntity>();
        try {
            DescribeAffectedEntitiesRequest request = new DescribeAffectedEntitiesRequest();

            //EntityFilter filter = new EntityFilter();
            request.setFilter(filter);

            DescribeAffectedEntitiesResult response = health.describeAffectedEntities(request);

            if (null != response) {
                lists = response.getEntities();
            }

        } catch (AmazonServiceException ase) {
            AwsException.printLog(logger, ase);
        } catch (AmazonClientException ace) {
            AwsException.printLog(logger, ace);
        }

        return lists;
    }




}