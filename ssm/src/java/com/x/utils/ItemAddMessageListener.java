package com.x.utils;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//activeMQ的监听器
@Component("itemAddMessageListener")
public class ItemAddMessageListener implements MessageListener {
    public void onMessage(Message message) {
        try {
            //此处可以对数据进行处理，比如存储，比如消息推送等
            System.out.println("监听到的消息"+((TextMessage)message).getText());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
