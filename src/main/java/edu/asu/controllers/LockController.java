package edu.asu.controllers;

import edu.asu.services.LockFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class LockController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    /**
     * This is a synchronous acquire lock call. If a lock is unable to be acquired, the thread will be put to sleep,
     * The request will be kept alive using http-keep-alive. When the lock is available the Thread will be awoken
     * and respond with a json object of information about the lock.
     *
     * #TODO this synchronous implementation was to simplifiy our original approach and ensure we get working software
     * as quickly as possible. todo is establish bidirectional communication between client and server, and send event
     * driven messages to the client when a lock becomes available.
     *
     * @param userId This is a unique string value that represents the user. Perferably something that is gaurenteed
     *               to be unique (per user account) like an email address. Providing a non unique value will causes
     *               errors. TODO find a way to issue userIds that are gaurenteed to by unique by the server.
     *
     * @param resourcePath Full Resource path starting from the root of the cloud synced folder
     *                     ex: /Dropbox/homework/hw1.txt Note that this is not the full canonical path
     *                     full canonical    path: /Users/james.kieley/Dropbox/homework/hw1.txt
     *                     expected Resource path: /Dropbox/homework/hw1.txt
     *
     * @param lockType This can either be "READ" or "WRITE"
     * @return JSON object containing information about the lock ex:
     *
     *
     * {
     *     "userId": "jkieley@asu.edu",
     *     "resourcePath": "/Dropbox/homework/hw1.txt",
     *     "lockType": "WRITE",
     *     "issueTime": "1457888814600",
     * }
     *
     *
     */
    @RequestMapping("/lock")
    @ResponseBody
    String lock(
            @RequestParam("userId") String userId,
            @RequestParam("resourcePath") String resourcePath,
            @RequestParam("lockType") String lockType
    ) {
        return LockFactory.acquireLock(userId, resourcePath, lockType);
    }

    @RequestMapping("/unlock")
    @ResponseBody
    String unlock(
            @RequestParam("userId") String userId,
            @RequestParam("resourcePath") String resourcePath,
            @RequestParam("lockType") String lockType
    ) {
        return LockFactory.releaseLock(userId, resourcePath, lockType);
    }



    public static void main(String[] args) throws Exception {
        SpringApplication.run(LockController.class, args);
    }
}
