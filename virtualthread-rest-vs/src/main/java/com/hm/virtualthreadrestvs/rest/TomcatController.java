package com.hm.virtualthreadrestvs.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/5/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
public class TomcatController {


    @GetMapping("get")
    public String get() {
        try {
//            System.out.println(Thread.currentThread());
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }
}