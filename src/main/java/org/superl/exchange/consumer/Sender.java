package org.superl.exchange.consumer;

import lombok.extern.log4j.Log4j;
import org.superl.exchange.consumer.service.consumerService;
import org.superl.exchange.consumer.service.senderService;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * ━━━━━━神兽出没━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * 　　 ┏┓　　　 ┏┓
 * 　　┏┛┻━━━━━━┛┻┓
 * ┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃     @desc:          作者很懒，啥都没写 ~~
 * 　　┃　　 ━　　 ┃
 * 　　┃　┳┛　 ┗┳       @Copyright(C):  superl@nepenthes.cn  at  2018/5/1 下午7:09
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
public class Sender {

    public static void main(String[] args){
        log.info("Start my senders...");
        startThread();
    }

    /**
     * 启动线程
     */
    private static void startThread(){

        //String[] queues = {"okex","binance","huobi","bittrex","bitmex"};
        String[] queues = {"okex","binance"};

        for (String queue: queues) {
            new senderService(queue).start();
        }
    }

}
