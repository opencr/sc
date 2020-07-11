package org.sc.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuhenghuo
 */
@RestController
public class TestController {

    @GetMapping("testMQ")
    public Object testMQ(){

        return "";
    }
}
