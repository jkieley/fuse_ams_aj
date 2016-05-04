package edu.asu.services;

import edu.asu.domain.Block;
import edu.asu.domain.LeaseResult;
import edu.asu.domain.Lock;
import edu.asu.domain.LockType;
import edu.asu.domain.Resource;
import edu.asu.domain.User;

/**
 * Created by james.kieley on 3/13/16.
 */
public class LockFactory {

    private static final String successJsonString = "{\"releaseLock\": \"success\"}";

    public static String acquireLock(String userId, String resourcePath, String lockTypeString, Integer blockId) {
        LockType lockType = LockType.get(lockTypeString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        Block block = resource.getBlockById(blockId);
        Lock lock = block.acquireLock(lockType);
        return getLockJsonString(user,resource,lock, block);
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
    private static String getLockJsonString(User user, Resource resource, Lock lock, Block block) {
        String output="";
        output+="{";
        output+="\"userId\": \""+user.getUserId()+"\",";
        output+="\"resourcePath\": \""+resource.getResourcePath()+"\",";
        output+="\"lockType\": \""+lock.getType().toString()+"\",";
        output+="\"md5\": \""+block.getMd5FromLastUpdate()+"\",";
        output+="\"blockId\": \""+block.getId().toString()+"\",";
        output+="\"issueTime\": \""+lock.getLockTime().toString()+"\"";
        output+="}";
        return output;
    }

    public static String releaseLock(String userId, String resourcePath, String lockTypeString, String md5FromLastUpdate, Integer blockId) {
        LockType lockType = LockType.get(lockTypeString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        Block block = resource.getBlockById(blockId);
        block.releaseLock(lockType, md5FromLastUpdate);
        return successJsonString;
    }

    public static String acquireLease(String userId, String resourcePath, Integer blockId) {
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        Block block = resource.getBlockById(blockId);
        LeaseResult leaseResult = block.acquireLease();
        return getacquireLeaseJsonString(resource, leaseResult, block);
    }

    private static String getacquireLeaseJsonString(Resource resource, LeaseResult leaseResult, Block block) {
        String output="";
        output+="{";
        output+="\"leaseKey\": \""+leaseResult.getLease().getNum()+"\",";
        output+="\"hasLease\": "+leaseResult.isHasOthers()+"\",";
        output+="\"md5\": \""+block.getMd5FromLastUpdate()+"\",";
        output+="\"blockId\": \""+block.getId().toString()+"\",";
        output+="}";
        return output;
    }

    public static String releaseLease(String userId, String resourcePath, String leaseKeyString, String md5FromLastUpdate, Integer blockId) {
        Integer leaseKey = Integer.parseInt(leaseKeyString);
        User user = UserFactory.getUser(userId);
        Resource resource = user.getResource(resourcePath);
        Block block = resource.getBlockById(blockId);
        block.releaseLease(leaseKey, md5FromLastUpdate);
        return successJsonString;
    }
}
