package cn.szzsi.dto;

import cn.szzsi.model.Customer;

/**
 * Created by Yishe on 8/7/2015.
 */
public class CustomerDto{
    private int id;
    private String username;
    private String nickname;
    private int location;

    public CustomerDto(Customer customer){


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
}
