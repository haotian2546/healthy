package cn.szzsi.dto;

import cn.szzsi.model.Consulter;
import cn.szzsi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yishe on 8/8/2015.
 */
public class ConsulterDto{
    private int id;
    private String openid;
    private String nickname;
    private String username;
    private String headurl;
    private int childsex;
    private String childname;
    private String phone;
    private String address;
    private Long childbirth;
    private long lastAskTime;

    public ConsulterDto(Consulter consulter){
        this.id = consulter.getInt("id");
        this.openid = consulter.getStr("openid");
        this.nickname = consulter.getStr("nickname");
        this.username = consulter.getStr("username");
        this.headurl = consulter.getStr("headurl");
        this.childsex = consulter.getInt("childsex");
        this.childname = consulter.getStr("childname");
        this.phone = consulter.getStr("phone");
        this.address = consulter.getStr("address");
        this.childbirth = consulter.getLong("childbirth");
//        this.lastAskTime = consulter.getLong("lastAskTime");
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

    public void setHeadurl(String headurl){
        this.headurl = headurl;
    }

    public int getChildsex(){
        return childsex;
    }

    public void setChildsex(int childsex){
        this.childsex = childsex;
    }

    public String getChildname(){
        return childname;
    }

    public void setChildname(String childname){
        this.childname = childname;
    }

    public Long getChildbirth(){
        return childbirth;
    }

    public void setChildbirth(Long childbirth){
        this.childbirth = childbirth;
    }

    public long getLastAskTime(){
        return lastAskTime;
    }

    public void setLastAskTime(long lastAskTime){
        this.lastAskTime = lastAskTime;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getOpenid(){
        return openid;
    }

    public void setOpenid(String openid){
        this.openid = openid;
    }
}
