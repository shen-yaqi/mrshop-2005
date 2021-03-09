package com.mr.rabbitmq.simple;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @ClassName SendMessage
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/9
 * @Version V1.0
 **/
public class SendMessage {

    //序列名称
    private final static String QUEUE_NAME = "simple_queue";

    //主函数
    public static void main(String[] arg) throws Exception {
        //获取连接 现在可以操作rabbitmq了
        Connection connection = RabbitmqConnectionUtil.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        /*
        param1 : 队列名称
        param2 : 是否持久化
        param3 : 是否排外
        param4 : 是否自动删除
        param5 : 额外参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //要发送的消息
        String msg = "good good study , day day up";

        //发送消息
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("消息发送成功");

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
