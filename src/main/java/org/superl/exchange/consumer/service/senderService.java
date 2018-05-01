package org.superl.exchange.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * ━━━━━━神兽出没━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * 　　 ┏┓　　　 ┏┓
 * 　　┏┛┻━━━━━━┛┻┓
 * ┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃     @desc:          作者很懒，啥都没写 ~~
 * 　　┃　　 ━　　 ┃
 * 　　┃　┳┛　 ┗┳       @Copyright(C):  superl@nepenthes.cn  at  2018/5/1 下午5:12
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
public class senderService extends Thread{

    private String[] nameList = {"okex","binance","huobi","bittrex","bitmex"};

    private String queueName;
    private Connection connection;
    private Channel channel;

    public senderService(String name) {
        this.queueName = name;
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

            channel.queueDeclare(queueName, true, false, false, null);

            for (int i = 0; i < 3000;) {
                String message = "NO. " + ++i;
                TimeUnit.MILLISECONDS.sleep(100);
                channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                log.info(message);
                //System.out.printf("(%1$s) %3$s\n", queueNAME, message);
            }
            channel.close();
            connection.close();
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
