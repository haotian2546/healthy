package cn.szzsi.dto;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import cn.szzsi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yishe on 8/7/2015.
 */
public class OrderDto{
    private int id;
    private String nickname;
    private String headurl;
    private String desc;
    private long time;
    private Chat message;

    public OrderDto(Order order,Consulter consulter){
        this.id = order.getInt("id");
        this.time = order.getLong("create_time");
        this.nickname = consulter.getStr("nickname");
        this.headurl = consulter.getStr("headurl");
        this.desc = Message.getOrderDesc(order.getInt("id"));
        this.message =  new Chat(Message.getOrderLastMessage(order.getInt("id")));
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getHeadurl(){
        if(StringUtils.isBlank(headurl)){
            return PathUtil.getDefaultHeadUrl();
        }
        return headurl;
    }

    public void setHeadurl(String headurl){
        this.headurl = headurl;
    }

    public Chat getMessage(){
        return message;
    }

    public void setMessage(Chat message){
        this.message = message;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public long getTime(){
        return time;                                                                                                     
    }

    public void setTime(long time){
        this.time = time;
    }

    public static final class Chat{
        private String content;
        private long time;

        public Chat(Message message){
            this.content = message.getStr("content");
            this.time = message.getLong("create_time");
        }

        public long getTime(){
            return time;
        }

        public void setTime(long time){
            this.time = time;
        }

        public String getContent(){
            return content;
        }

        public void setContent(String content){
            this.content = content;
        }
    }
}
