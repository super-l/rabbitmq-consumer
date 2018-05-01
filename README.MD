### 说明

rabbitMQ消息生产与消费实例。支持多线程多队列同时消费，每个队列同时也支持自定义并发消费线程！采用Qos实现公平分发。

### 用到的库

具体查看pom.xml文件。主要就下面3个。
log4j
com.rabbitmq
org.projectlombok

### 运行方法

修改senderService.java以及consumerService.java中的rabbitMQ的连接参数信息

生产者：运行Sender

消费者：运行Consumer

本例中创建了2个队列，分别是okex和binance。

### 其他说明

生产者循环发送3000条消息。

消费端，每个队列都默认用3个线程同时消费

