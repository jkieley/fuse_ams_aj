package edu.asu.domain;

/**
 * Created by james.kieley on 4/20/16.
 */
public class LeaseResult {
    private Lease lease;
    private boolean hasOthers;

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public boolean isHasOthers() {
        return hasOthers;
    }

    public void setHasOthers(boolean hasOthers) {
        this.hasOthers = hasOthers;
    }
}
