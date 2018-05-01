package org.superl.exchange.consumer.service;

import com.rabbitmq.client.*;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeoutException;

/**
 * ━━━━━━神兽出没━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * 　　 ┏┓　　　 ┏┓
 * 　　┏┛┻━━━━━━┛┻┓
 * ┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃     @desc:          作者很懒，啥都没写 ~~
 * 　　┃　　 ━　　 ┃
 * 　　┃　┳┛　 ┗┳       @Copyright(C):  superl@nepenthes.cn  at  2018/5/1 下午5:22
 * 　　┃　　　　　 ┃
 * 　　┃　　 ┻　　       @author:        superl (qq:86717375)
 * 　　┃　　　　　 ┃     @team:          Nepenthes Security Team(忘忧草安全团队)
 * 　　┗━┓　　　┏━┛
 * 　　　┃　　  ┃        神兽保佑,代码无bug
 * 　　　┃　　  ┃
 * 　　　┃　　  ┗━━━┓    @link:          http://www.superl.org  https://github.com/super-l
 * 　　　┃　　　　   ┣┓
 * 　　　┃　　　　   ┏┛   Code is far away from bug with the animal protecting
 * 　　　┗┓┓┏━ ┳┓━━━┛
 * 　　　 ┃┫┫ ┃┫┫
 * 　　　 ┗┻┛ ┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 */
@Log4j
public class consumerService extends Thread{

    private String queueName;
    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;

    public consumerService(String queue){
        this.queueName = queue;
    }

    public void run(){

        ConnectionFactory factory = new ConnectionFactory();

        //设置RabbitMQ相关信息
        factory.setHost("127.0.0.0");
        factory.setUsername("superl");
        factory.setPassword("superl#a");
        factory.setPort(5672);
        factory.setVirtualHost("myspider");

        try{
            connection = factory.newConnection();
            channel = connection.createChannel();

            // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println("Waiting for messages. To exit press CTRL+C");

            // 创建队列消费者
            consumer = new QueueingConsumer(channel);

            // 设置最大服务消息接收数量
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            boolean ack = false; // 是否自动确认消息被成功消费
            channel.basicConsume(queueName, ack, consumer); // 指定消费队列

            doTask();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void doTask() throws IOException,TimeoutException,InterruptedException{
        while (true) {
            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info(" [x] Received '" + this.queueName+" "+message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            //Thread.sleep(2000);
        }
    }

}
