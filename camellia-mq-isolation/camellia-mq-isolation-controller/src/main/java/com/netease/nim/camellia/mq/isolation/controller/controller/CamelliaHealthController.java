package com.netease.nim.camellia.mq.isolation.controller.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caojiajun on 2023/1/15
 */
@RestController
@RequestMapping("/camellia/health")
public class CamelliaHealthController {

    private static final String OK = "{\"code\":200}";
    private static final String ERROR = "{\"code\":500}";

    @RequestMapping(value = "/status")
    public ResponseEntity<String> status() {
        if (CamelliaMqIsolationControllerStatus.getStatus() == CamelliaMqIsolationControllerStatus.Status.ONLINE) {
            return new ResponseEntity<>(OK, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>(OK, HttpStatus.OK);
    }

    @RequestMapping(value = "/online")
    public ResponseEntity<String> online() {
        CamelliaMqIsolationControllerStatus.setStatus(CamelliaMqIsolationControllerStatus.Status.ONLINE);
        return new ResponseEntity<>(OK, HttpStatus.OK);
    }

    @RequestMapping(value = "/offline")
    public ResponseEntity<String> offline() {
        CamelliaMqIsolationControllerStatus.setStatus(CamelliaMqIsolationControllerStatus.Status.OFFLINE);
        if (CamelliaMqIsolationControllerStatus.isIdle()) {
            return new ResponseEntity<>(OK, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
