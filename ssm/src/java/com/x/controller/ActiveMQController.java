package com.x.controller;

import com.x.api.ConsumerService;
import com.x.api.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Xia_Q on 2018/3/24.
 */

@Controller
public class ActiveMQController {

    @Resource(name = "queueDestination")
    private Destination destination;

    //队列消息生产者
    @Autowired
    private ProducerService producer;

    //队列消息消费者
    @Autowired
    private ConsumerService consumer;



    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public void send(HttpServletRequest request, HttpServletResponse response, @RequestParam String msg) {
        producer.sendMessage(msg);
    }


    //此处用不到，因为已经集成进入了spring
    @RequestMapping(value= "/receive",method = RequestMethod.GET)
    @ResponseBody
    public Object receive(){
        TextMessage tm = consumer.receive(destination);
        System.out.println(tm.toString());
        return tm;
    }
}
