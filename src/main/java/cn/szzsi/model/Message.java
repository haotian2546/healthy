package cn.szzsi.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Yishe on 8/6/2015.
 */
public class Message extends Model<Message>{
    public static final Message dao = new Message();

    public static final Message createConsulterMessage(Order order,String content){
        Message message = new Message();
        message.set("order_id",order.getInt("id"));
        message.set("sender_id",order.getInt("consulter_id"));
        message.set("sender_type",0);
        message.set("create_time",System.currentTimeMillis());
        message.set("content",content);
        message.save();
        return message;
    }
}
