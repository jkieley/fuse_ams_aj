package edu.asu.domain;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by james.kieley on 3/12/16.
 */
public class Resource {
    public String getResourcePath() {
        return resourcePath;
    }

    private String resourcePath;
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

    public synchronized Lock releaseLock(LockType lockType) {
        this.notifyAll();
        return lockQue.remove();
    }
}
