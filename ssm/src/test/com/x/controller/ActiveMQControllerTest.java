package com.x.controller;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml",
        "classpath*:spring-mvc.xml",
        "classpath*:mybatis-config.xml",
        "classpath*:activemq.xml"})
public class ActiveMQControllerTest {

    @Test
    public void provider() {
        // 第一步： 构造ConnectionFactory实例对象
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        try {
            // 第二步：使用ConnectionFactory对象创建connection对象
            Connection connection = factory.createConnection("admin", "admin");
            System.out.println(connection);
            // 第三步：开启连接
            connection.start();
            // 第四步：使用connetion对象创建Session对象
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            // 第五步：使用Session对象创建Destination对象（topic，quene）
            //点对点
            Queue queue = session.createQueue("spring-queue");
            //发布订阅
//        Topic topic = session.createTopic("test-topic1");
            // 第六步：使用session创建Provider
            MessageProducer messageProducer = session.createProducer(queue);
            // 第七步：创建一个Message对象，创建TextMessage对象
            Message message = session.createTextMessage();
            message.setStringProperty("test1", "国庆大放送");
            // 第八步：使用Provider发送消息
            messageProducer.send(message);
            // 第九步：使用session提交
            session.commit();
            // 第十步：关闭；资源
            session.close();
//            InputStream is = System.in;
//            is.read();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void consumer() {
        //第一步： 构造ConnectionFactory实例对象
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        try {
            //第二步：使用ConnectionFactory对象创建connection对象
            Connection connection = factory.createConnection("admin", "admin");
            //第三步：开启连接
            connection.start();
            //第四步：使用connetion对象创建Session对象
            Session session = connection.createSession(true, 0);
            //第五步：使用Session对象创建Destination对象（topic，quene）
            Queue queue = session.createQueue("test-quene1");//点对点
            //发布订阅
//            Topic topic =  session.createTopic("test-topic1");
            //第六步：使用session创建MessageConsumer对象
            MessageConsumer consumer = session.createConsumer(queue);
            //第七步：接收消息（两种方式：1）使用监听方式（常用） 2）循环接收：receive）
//            while (true){
            Message message = consumer.receive();
            System.out.println("接到的消息是：" + message.getStringProperty("test1"));
//            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}