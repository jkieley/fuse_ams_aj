package edu.asu.domain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by james.kieley on 3/12/16.
 */
public class User {
    private String userId;
    private ConcurrentHashMap<String,Resource> resources = new ConcurrentHashMap<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String userId) {
        this.userId = userId;
    }

    public Resource getResource(String resourcePath) {
        Resource resource = resources.get(resourcePath);
        if(resource == null){
            resource = new Resource(resourcePath);
            resources.put(resourcePath,resource);
        }
        return resource;
    }
}
