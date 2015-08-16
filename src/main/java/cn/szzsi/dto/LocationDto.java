package cn.szzsi.dto;

import cn.szzsi.model.Location;

/**
 * Created by Yishe on 8/16/2015.
 */
public class LocationDto{
    private int code;
    private String name;

    public LocationDto(Location location){
        this.code = location.getInt("code");
        this.name = location.getStr("name");
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
