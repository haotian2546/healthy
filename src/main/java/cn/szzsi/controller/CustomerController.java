package cn.szzsi.controller;

import cn.szzsi.dto.Msg;
import cn.szzsi.model.Customer;
import com.jfinal.core.Controller;

/**
 * Created by Yishe on 8/6/2015.
 */
public class CustomerController extends Controller{

    /**
     * 医生注册
     */
    public void reg(){
        String username = getPara("username");
        String password = getPara("password");
        String nickname = getPara("nickname");
        String headurl = getPara("headurl");
        String memo = getPara("memo");
        String department = getPara("department");
        int location = getParaToInt("location");
//        Customer.register(username,password,nickname,headurl,memo);
        renderJson(Msg.SUCCESS);
    }

    public void login(){

    }

    public void order(){

    }

}
