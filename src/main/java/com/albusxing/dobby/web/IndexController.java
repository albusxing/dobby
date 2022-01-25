package com.albusxing.dobby.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liguoqing
 */
@RestController
public class IndexController {

    @RequestMapping({"/index", "/"})
    public String index() {
        return "Hello, Spring Boot MSA";
    }
}
