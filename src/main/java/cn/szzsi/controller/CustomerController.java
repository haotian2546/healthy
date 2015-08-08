package cn.szzsi.controller;

import cn.szzsi.dto.CustomerDto;
import cn.szzsi.dto.Msg;
import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.util.HuanxinUtil;
import cn.szzsi.model.Customer;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * Created by Yishe on 8/6/2015.
 */
public class CustomerController extends Controller{

    /**
     * 医生注册
     */
    @Clear(AuthInterceptor.class)
    public void reg(){
        String username = getPara("username");
        String password = getPara("password");
        String nickname = getPara("nickname");
        String headurl = getPara("headurl");
        String memo = getPara("memo");
        String department = getPara("department");
        int location = getParaToInt("location",Integer.valueOf(0));
        Customer customer = Customer.register(username,password,nickname,headurl,memo,department,location);
        HuanxinUtil.register(customer);
        renderJson(Msg.SUCCESS);
    }

    @Clear(AuthInterceptor.class)
    public void login(){
        String username = getPara("username");
        String password = getPara("password");
        Customer customer = Customer.getByUsername(username);
        if(customer == null || !customer.getStr("password").equals(password)){
            renderJson(Msg.fail(1,"用户名或密码错误"));
            SessionUtil.setCustomer(this,customer);
        }else{
            renderJson(Msg.success(new CustomerDto(customer)));
        }
    }

    public void order(){

    }

}
