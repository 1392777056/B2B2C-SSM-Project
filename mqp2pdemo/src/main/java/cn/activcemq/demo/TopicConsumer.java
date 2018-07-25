package cn.activcemq.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/25 22:17
 */
public class TopicConsumer {

    public static void main(String[] args) throws Exception {
        // 1.同样，还是去创建工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.25.129:61616");
        // 2.获取连接
        Connection connection = connectionFactory.createConnection();
        // 3.开启连接
        connection.start();
        // 4.创建session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.读取生成者的队列对象 --- 必须和需要查的生成者的队列对象名称要保持一致。
        Topic topic = session.createTopic("text-queue");
        // 6.创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 7.设置监听消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                // 获取消息 --- 和生成者那边的类型要保持一致。
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("接受的信息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 8.自己按情况设置线程
        System.in.read();   //如果键盘一直没有输入，那么线程会一致保持等待状态。
        // 9.关闭资源
        consumer.close();
        session.close();
        connection.close();

    }

}
