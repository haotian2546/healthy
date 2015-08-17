package cn.szzsi.dto;

import cn.szzsi.model.Customer;
import cn.szzsi.model.Location;
import cn.szzsi.util.SessionUtil;

/**
 * Created by Yishe on 8/13/2015.
 */
public class CustomerDto{
    private int id;
    private String username;
    private String nickname;
    private int location;
    private String locationName;
    private int isOnline;

    public CustomerDto(Customer customer){
        this.id = customer.getInt("id");
        this.username = customer.getStr("username");
        this.nickname = customer.getStr("nickname");
        this.location = customer.getInt("location");
        Location loc =Location.getByCode(location);
        this.locationName = loc!=null?loc.getStr("name"):"";
        this.isOnline = SessionUtil.isOnline(id)?1:0;
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

    public String getLocationName(){
        return locationName;
    }

    public void setLocationName(String locationName){
        this.locationName = locationName;
    }
}
