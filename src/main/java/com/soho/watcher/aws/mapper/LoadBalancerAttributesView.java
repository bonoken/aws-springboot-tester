package com.soho.watcher.aws.mapper;

import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerAttributes;

public class LoadBalancerAttributesView {
    private String name = null;
    private LoadBalancerAttributes attributes = null;

    public String getName() {
        return name;
    }

    public LoadBalancerAttributes getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(LoadBalancerAttributes attributes) {
        this.attributes = attributes;
    }
}