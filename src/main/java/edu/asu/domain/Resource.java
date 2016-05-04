package edu.asu.domain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by james.kieley on 3/12/16.
 */
public class Resource {
    public String getResourcePath() {
        return resourcePath;
    }

    private String resourcePath;
    private ConcurrentHashMap<Integer, Block> blocksMap = new ConcurrentHashMap<>();

    public Resource(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public synchronized Block getBlockById(Integer id){
        Block block = blocksMap.get(id);
        if(block == null){
            block = new Block(id);
            blocksMap.put(id,block);
        }
        return block;
    }
}
