package edu.asu.services;

import edu.asu.domain.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by james.kieley on 3/13/16.
 */
public class UserFactory {

    private static ConcurrentHashMap<String,User> userMap = new ConcurrentHashMap<String,User>();

    public static User getUser(String userId) {
        User user = userMap.get(userId);
        if(user == null){
            user = new User(userId);
            userMap.put(userId,user);
        }
        return user;
    }
}
