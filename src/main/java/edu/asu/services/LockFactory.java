package edu.asu.services;

import edu.asu.domain.LeaseResult;
import edu.asu.domain.Lock;
import edu.asu.domain.LockType;
import edu.asu.domain.Resource;
import edu.asu.domain.User;

/**
 * Created by james.kieley on 3/13/16.
 */
public class LockFactory {

    private static final String successJsonString = "{releaseLock: \"success\"}";

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
        output+="\"md5\": \""+resource.getMd5FromLastUpdate()+"\",";
        output+="\"issueTime\": \""+lock.getLockTime().toString()+"\"";
        output+="}";
        return output;
    }

    public static String releaseLock(String userId, String resourcePath, String lockTypeString, String md5FromLastUpdate) {
        LockType lockType = LockType.get(lockTypeString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        resource.releaseLock(lockType, md5FromLastUpdate);
        return successJsonString;
    }

    public static String acquireLease(String userId, String resourcePath) {
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        LeaseResult leaseResult = resource.acquireLease();
        return "{leaseKey: \""+leaseResult.getLease().getNum()+"\", hasLease: \""+leaseResult.isHasOthers()+"\"}";
    }

    public static String releaseLease(String userId, String resourcePath, String leaseKeyString) {
        Integer leaseKey = Integer.parseInt(leaseKeyString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        resource.releaseLease(leaseKey);
        return successJsonString;
    }
}
