package com.soho.watcher.aws.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.health.AWSHealth;
import com.amazonaws.services.health.model.AffectedEntity;
import com.amazonaws.services.health.model.EntityFilter;
import com.amazonaws.services.health.model.Event;
import com.soho.watcher.aws.api.management.AwsHealth;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthTest {

    /**
     * log
     */
    private static final Logger logger = LogManager.getLogger(HealthTest.class);

    @Autowired
    private AwsConfiguration config;

    private AwsHealth health;

    @Test
    public void contextLoads() {
        AwsClientFactory factory = new AwsClientFactory(config);
        AWSHealth awsHealth = factory.healthClient();
        this.health = new AwsHealth(awsHealth);


        // TO-DO unit testing
/*        List<Event> events = this.getEvent(health);

        for (Event event : events) {
            EntityFilter filter = new EntityFilter();
            filter.withEventArns(event.getArn());
            getAffectedEntity(health, filter);
        }*/
    }

    public Collection<String> getRegion() {
        Collection<String> regions = new ArrayList<String>();
        //regions.add("ap-northeast-1");
        regions.add("ap-northeast-2");
        //regions.add("ap-southeast-1");
        //regions.add("ap-southeast-2");
        //regions.add("eu-west-1");
        //regions.add("sa-east-1");
        //regions.add("us-east-1");

        return regions;
    }

    public List<Event> getEvent(AwsHealth health) {

/*        Collection<String> regions = this.getRegion();
        List<Event> events = health.listEvents(regions);
        System.out.println("events : " + events.toString());*/
        List<Event> events = health.listEvents();
        return events;
    }

    public void getAffectedEntity(AwsHealth health, EntityFilter filter) {

        //EntityFilter filter = new EntityFilter();
        List<AffectedEntity> affectedEntities = health.listAffectedEntities(filter);

        System.out.println("affectedEntities : " + affectedEntities.toString());
    }


}
