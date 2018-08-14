package com.x.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 做高并发时的缓存锁用
 * Created by Xia_Q on 2018/3/20.
 */

@Component
public class RedisLock {

    private static final Logger logger= LoggerFactory.getLogger(RedisLock.class);


    @Autowired
    private RedisUtil redisUtil;


    /**
     * 加锁，同时解决多线程和可能出现异常的情况
     * @param key   productID
     * @param value  时间，当前时间 + 超时规定的时间
     * @return
     */
    public  boolean lock(String key, String value){

        //如果不存在key,表示可以存入
        if (redisUtil.setIfAbsent(key,value)){
            return true;
        }
        //如果锁过期
        //currentValue = A  这两个线程都是B,其中一个线程拿到锁
        String currentValue = (String) redisUtil.get(key);
        //时间不为空，并且时间小于当前时间
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){
            //存储的时间小于当前的时间，获取上一个锁的时间，并且重新设置一个值进去
            String oldValue = (String) redisUtil.getAndSet(key,value);
            //老的时间不为空并且老的时间等于之前得到的时间
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return  false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public  void  unlock(String key,String value){
        try {
            String currentValue = (String) redisUtil.get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisUtil.delete(key);
            }
        }catch (Exception e){
            logger.error("解锁异常");
        }

    }
}
