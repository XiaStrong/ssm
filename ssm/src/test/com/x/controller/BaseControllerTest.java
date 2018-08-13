package com.x.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
        "classpath*:spring-mvc.xml",
        "classpath*:mybatis-config.xml"
        })
public class BaseControllerTest {

    @Test
    public void say(){
        System.out.println("呼啦啦");
    }
}