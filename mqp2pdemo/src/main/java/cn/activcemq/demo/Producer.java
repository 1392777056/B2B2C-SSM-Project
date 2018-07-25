package cn.activcemq.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/25 21:51
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        // 1.创建工厂并连接activeMQ的地址,选择使用TCP连接，端口号是61616
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.25.129:61616");
        // 2.获取连接
        Connection connection = connectionFactory.createConnection();
        // 3.开启连接
        connection.start();
        // 4.获取session (参数1：是否开启事务 参数2：消息的确认模式)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建队列对象
        Queue queue = session.createQueue("text-queue");
        // 6.创建消息生成者
        MessageProducer producer = session.createProducer(queue);
        // 7.创建消息
        TextMessage textMessage = session.createTextMessage("this is a cool boy");
        // 8.发送消息
        producer.send(textMessage);
        // 9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}
