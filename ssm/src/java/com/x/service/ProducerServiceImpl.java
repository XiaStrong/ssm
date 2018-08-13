package com.x.service;

import com.x.api.ProducerService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Resource(name = "queueDestination")
    private Destination destination;

    @Override
    public void sendMessage(final String msg) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                System.out.println("发送了消息！");
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(msg);
                return textMessage;
            }
        });
    }
}
