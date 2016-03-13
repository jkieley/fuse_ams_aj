package edu.asu.domain;

/**
 * Created by james.kieley on 3/12/16.
 */
public class Lock {
    private LockType type;
    private Long lockTime;

    public Long getLockTime() {
        return lockTime;
    }

    public LockType getType() {
        return type;
    }

    public Lock(LockType type) {
        this.type = type;
        this.lockTime = System.currentTimeMillis();
    }
}
