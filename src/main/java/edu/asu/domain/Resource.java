package edu.asu.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by james.kieley on 3/12/16.
 */
public class Resource {
    public String getResourcePath() {
        return resourcePath;
    }

    private String resourcePath;
    private String md5FromLastUpdate ="N/A";
    private int maxLease = 0;

    private ConcurrentHashMap<Integer,Lease> leaseMap = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Lock> lockQue = new ConcurrentLinkedQueue<>();

    public Resource(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * #TODO allow for concurrent read locks
     * @param type
     * @return
     */
    public synchronized Lock acquireLock(LockType type){
        while(!lockQue.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Lock lock = new Lock(type);
        lockQue.add(lock);
        return lock;
    }

    public synchronized Lock releaseLock(LockType lockType, String md5FromLastUpdate) {
        this.setMd5FromLastUpdate(md5FromLastUpdate);
        Lock lock = lockQue.remove();
        this.notifyAll();
        return lock;
    }

    public String getMd5FromLastUpdate() {
        return md5FromLastUpdate;
    }

    public void setMd5FromLastUpdate(String md5FromLastUpdate) {
        if(!md5FromLastUpdate.isEmpty()){
            this.md5FromLastUpdate = md5FromLastUpdate;
        }
    }

    public synchronized LeaseResult acquireLease() {
        Lease lease = createLease();

        LeaseResult leaseResult = new LeaseResult();
        leaseResult.setHasOthers(leaseMap.size() > 0);
        leaseResult.setLease(lease);

        leaseMap.put(lease.getNum(),lease);

        return leaseResult;
    }

    public synchronized void releaseLease(Integer leaseKey, String md5FromLastUpdate) {
        this.setMd5FromLastUpdate(md5FromLastUpdate);
        leaseMap.remove(leaseKey);
    }

    private Lease createLease() {
        maxLease++;
        Lease lease = new Lease(maxLease);
        return lease;
    }
}
