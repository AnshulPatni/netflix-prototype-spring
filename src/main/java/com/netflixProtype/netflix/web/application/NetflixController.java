package com.netflixProtype.netflix.web.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/")
public class NetflixController {

    @RequestMapping(method = RequestMethod.GET)
    public String getHomePage(){
        return "homePage";
    }
}
