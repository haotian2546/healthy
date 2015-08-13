package cn.szzsi.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

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

    public static final Message createCustomerMessage(Order order,String content){
        Message message = new Message();
        message.set("order_id",order.getInt("id"));
        message.set("sender_id",order.getInt("customer_id"));
        message.set("sender_type",1);
        message.set("create_time",System.currentTimeMillis());
        message.set("content",content);
        message.save();
        return message;
    }

    public static final String getOrderDesc(int orderId){
        Message message = dao.findFirst("select * from message where order_id=? and sender_type=0 order by create_time asc",orderId);
        if(message != null){
            return message.getStr("content");
        }
        return null;
    }

    public static final Page<Message> getPage(int pageNo,int pageSize,int orderId,Long time){
        StringBuilder sql = new StringBuilder();
        sql.append("from message where order_id=? ");
        if(time != null){
            sql.append("and create_time<=").append(time.longValue());
        }
        return dao.paginate(pageNo,pageSize,"select * ",sql.toString(),orderId);
    }


}
