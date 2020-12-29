package com.example.resources;

import com.example.model.DbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/test")
public class TestResource {

    @Value("${greeting: default value}")
    private String greeting;

    @Value("${my.greeting: greeting from cloud is not found}")
    private String myGreetingFromCloud;     //refresh by invoking http://localhost:4046/actuator/refresh

    @Autowired
    private DbProperties dbProperties;

    @GetMapping()
    public String getData(){
       return greeting + myGreetingFromCloud + dbProperties.getUserName() + dbProperties.getDriverClassName();
    }
}
