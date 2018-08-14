package com.x.controller;

import com.x.api.UserService;
import com.x.constant.RedisConstant;
import com.x.pojo.Users;
import com.x.utils.RedisLock;
import com.x.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private UserService userService;

    //对于@ResponseBody 返回为字符串的，请求部分需按如下写，不然返回的字符串在界面上会显示乱码，此中仅针对单个方法
//    @RequestMapping(value = "/redis",produces = "application/json; charset=utf-8")
    //第二种见spring-mvc.xml

    //测试一般的redis存取数据
    @RequestMapping("/redis")
    @ResponseBody
    public String testRedis(){

        String name = (String) redisUtil.get("name");
        if (StringUtils.isBlank(name)){
            boolean set = redisUtil.set("name", "夏强呵呵呵呵呵呵");
            if (set){
                return "redis取出的值 " +redisUtil.get("name");
            }else {
                return "错误信息";
            }
        }else {
            redisUtil.del("name");
            String name1 = (String) redisUtil.get("name");
            if (StringUtils.isBlank(name1)){
                return  "删除成功";
            }else {
                return  "删除失败";
            }
        }
    }

    //分布式锁操作
    @RequestMapping("/lock")
    @ResponseBody
    public String testLock(){
        //加锁
        long time = System.currentTimeMillis() + RedisConstant.TIMEOUT;
        if (!redisLock.lock("lock", String.valueOf(time))) {
            return "人太多了，请再试试";
        }

        //此处进行逻辑操作，若是设置的超时时间过长，在逻辑操作后可自行解锁
        for (int i=0;i<=1000000;i++){
            System.out.println("我在操作-------------------"+i);
            if (i==1000000){
                //解锁
                Users users = new Users();
                users.setUserAge(10);
                users.setRoleId(1);
                users.setUserName("哈哈哈哈");
                users.setUserTruename("呵呵呵");
                this.userService.insert(users);
                redisLock.unlock("lock", String.valueOf(time));
            }
        }
        return "操作成功";
    }

    @RequestMapping("/update")
    public void update(){
        Users users = new Users();
        users.setUserId(3);
        users.setUserAge(10);
        users.setRoleId(1);
        users.setUserName("哈哈102");
        users.setUserTruename("呵呵呵001");
        this.userService.update(users);
    }

}
