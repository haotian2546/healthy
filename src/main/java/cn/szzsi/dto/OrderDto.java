package cn.szzsi.dto;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;

/**
 * Created by Yishe on 8/7/2015.
 */
public class OrderDto{
    private int id;
    private String nickname;
    private String headurl;
    private String desc;
    private long time;

    public OrderDto(Order order,Consulter consulter){
        this.id = order.getInt("id");
        this.time = consulter.getLong("create_time");
        this.nickname = consulter.getStr("nickname");
        this.headurl = consulter.getStr("headurl");
        this.desc = Message.getOrderDesc(order.getInt("id"));;
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
        return headurl;
    }

    public void setHeadurl(String headurl){
        this.headurl = headurl;
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
}
