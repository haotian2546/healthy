package cn.szzsi.dto;

import cn.szzsi.model.Customer;

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
    private int integral;
    private String memo;
    private int location;

    public CustomerDetailDto(Customer customer){
        this.id = customer.getInt("id");
        this.username = customer.getStr("username");
        this.nickname = customer.getStr("nickname");
        this.headurl = customer.getStr("headurl");
        this.location = customer.getInt("location");
        this.company = customer.getStr("company");
        this.department = customer.getStr("department");
        this.memo = customer.getStr("memo");
        this.integral = customer.getInt("integral");
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

    public int getIntegral(){
        return integral;
    }

    public void setIntegral(int integral){
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
}
