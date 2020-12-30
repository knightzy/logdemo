package com.zylogtest.demo.controller;


import com.zylogtest.demo.aop.domeAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/log")
@domeAspect
public class LogTest {

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        log.info("output info");
        log.warn("output warn");
        log.error("output error");
        log.debug("output debug");
        return "hello world";
    }


}
