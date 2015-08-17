package cn.szzsi.dto;

import cn.szzsi.model.Customer;
import cn.szzsi.model.Location;
import cn.szzsi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yishe on 8/15/2015.
 */
public class CustomerDetailDto{
    private int id;
    private String username;
    private String nickname;
    private String headurl;
    private String company;
    private String department;
    private long integral;
    private String memo;
    private int location;
    private String locationName;

    public CustomerDetailDto(Customer customer){
        this.id = customer.getInt("id");
        this.username = customer.getStr("username");
        this.nickname = customer.getStr("nickname");
        this.headurl = customer.getStr("headurl");
        this.location = customer.getInt("location");
        this.company = customer.getStr("company");
        this.department = customer.getStr("department");
        this.memo = customer.getStr("memo");
        this.integral = customer.getLong("integral");
        Location loc =Location.getByCode(location);
        this.locationName = loc!=null?loc.getStr("name"):"";
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public long getIntegral(){
        return integral;
    }

    public void setIntegral(long integral){
        this.integral = integral;
    }

    public String getMemo(){
        return memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
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

    public String getHeadurl(){
        if(StringUtils.isBlank(headurl)){
            return PathUtil.getDefaultHeadUrl();
        }
        return headurl;
    }

    public void setHeadurl(String headurl){
        this.headurl = headurl;
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
