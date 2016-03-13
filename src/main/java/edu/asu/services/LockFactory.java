package edu.asu.services;

import edu.asu.domain.Lock;
import edu.asu.domain.LockType;
import edu.asu.domain.Resource;
import edu.asu.domain.User;

/**
 * Created by james.kieley on 3/13/16.
 */
public class LockFactory {
    public static String acquireLock(String userId, String resourcePath, String lockTypeString) {
        LockType lockType = LockType.get(lockTypeString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        Lock lock = resource.acquireLock(lockType);
        return getLockJsonString(user,resource,lock);
    }

    /**
     *
     * @return
     * {
     *     "userId": "jkieley@asu.edu",
     *     "resourcePath": "/Dropbox/homework/hw1.txt",
     *     "lockType": "WRITE",
     *     "issueTime": "1457888814600",
     * }
     */
    private static String getLockJsonString(User user, Resource resource, Lock lock) {
        String output="";
        output+="{";
        output+="\"userId\": \""+user.getUserId()+"\",";
        output+="\"resourcePath\": \""+resource.getResourcePath()+"\",";
        output+="\"lockType\": \""+lock.getType().toString()+"\",";
        output+="\"issueTime\": \""+lock.getLockTime().toString()+"\",";
        output+="}";
        return output;
    }

}
