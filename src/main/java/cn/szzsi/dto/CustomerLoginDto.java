package cn.szzsi.dto;

import cn.szzsi.model.Customer;
import cn.szzsi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yishe on 8/7/2015.
 */
public class CustomerLoginDto{
    private int id;
    private String username;
    private String nickname;
    private String headurl;
    private int location;
    private String token;
    private String chatid;
    private String chatpd;

    public CustomerLoginDto(Customer customer){
        this.id = customer.getInt("id");
        this.username = customer.getStr("username");
        this.nickname = customer.getStr("nickname");
        this.headurl = customer.getStr("headurl");
        this.location = customer.getInt("location");
        this.chatid = customer.getStr("username");
        this.chatpd = customer.getStr("chatpd");
        this.token = customer.getStr("token");
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getHeadurl(){
        if(StringUtils.isBlank(headurl)){
            return PathUtil.getDefaultHeadUrl();
        }
        return headurl;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setHeadurl(String headurl){
        this.headurl = headurl;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public int getLocation(){
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }

    public String getChatid(){
        return chatid;
    }

    public void setChatid(String chatid){
        this.chatid = chatid;
    }

    public String getChatpd(){
        return chatpd;
    }

    public void setChatpd(String chatpd){
        this.chatpd = chatpd;
    }
}
