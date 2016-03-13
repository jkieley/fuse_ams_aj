package edu.asu.domain;

/**
 * Created by james.kieley on 3/13/16.
 */
public enum  LockType {
    READ,WRITE;

    public static LockType get(String type){
        switch (type){
            case "READ":
                    return READ;
            case "WRITE":
                    return WRITE;
            default:
                    throw new IllegalArgumentException("Unrecognized Lock Type: "+type);
        }

    }
}
