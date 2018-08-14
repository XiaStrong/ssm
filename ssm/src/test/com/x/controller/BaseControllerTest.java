package com.x.controller;

import com.x.utils.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
        "classpath*:spring-mvc.xml",
        "classpath*:redis.xml",
        "classpath*:mybatis-config.xml"
        })
public class BaseControllerTest {

    @Autowired
    private RedisLock redisLock;
    //    设置超时时间为10秒钟
    private static final int TIMEOUT = 10 * 1000;



    @Test
    public void say(){
        System.out.println("呼啦啦");
    }



    @Test
    public void testRedisLock() throws Exception {

        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock("productId", String.valueOf(time))) {
            throw new RuntimeException("人太多了，请再试试");
        }
        //此处进行逻辑操作

        //解锁
//        redisLock.unlock("productId", String.valueOf(time));


    }


}