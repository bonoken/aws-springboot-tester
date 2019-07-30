package com.soho.watcher.aws.mapper;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.amazonaws.services.elasticloadbalancing.model.InstanceState;

public class InstanceStateView {
    private String name = null;
    private List<InstanceState> instances = new ArrayList<InstanceState>();

    public String getName() {
        return name;
    }

    public List<InstanceState> getInstances() {
        return Collections.unmodifiableList(instances);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstances(List<InstanceState> instances) {
        this.instances = new ArrayList<InstanceState>(instances);
    }
}
