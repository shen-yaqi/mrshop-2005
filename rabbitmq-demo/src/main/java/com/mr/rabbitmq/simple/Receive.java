package com.mr.rabbitmq.simple;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;

import javax.sound.midi.Soundbank;
import java.io.IOException;

/**
 * @ClassName Receive
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/9
 * @Version V1.0
 **/
public class Receive {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = RabbitmqConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                //System.out.println(1/0);
                System.out.println(new String(body, "UTF-8"));
                //手动确认消息已经收到
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        /*
        param1 : 队列名称
        param2 : 是否自动确认
        param3 : 消费者
         */
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }

}
