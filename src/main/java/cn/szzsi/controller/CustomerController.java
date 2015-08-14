package cn.szzsi.controller;

import cn.szzsi.dto.CustomerDto;
import cn.szzsi.dto.CustomerLoginDto;
import cn.szzsi.dto.Msg;
import cn.szzsi.dto.OrderDto;
import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.Require;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Order;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yishe on 8/6/2015.
 */
public class CustomerController extends Controller{

    private static final Logger logger = Logger.getLogger(CustomerController.class);
    /**
     * 医生注册
     */
    @Clear(AuthInterceptor.class)
    @Require({"username","password","nickname"})
    public void reg(){
        String username = getPara("username");
        Customer customer = Customer.getByUsername(username);
        if(customer != null){
            renderJson(Msg.fail(1,"该用户名已被注册"));
            return;
        }
        String password = getPara("password");
        String nickname = getPara("nickname");
        String headurl = getPara("headurl");
        String memo = getPara("memo");
        String department = getPara("department");
        int location = getParaToInt("location",Integer.valueOf(0));
        Customer.register(username,password,nickname,headurl,memo,department,location);
        renderJson(Msg.SUCCESS);
    }

    @Clear(AuthInterceptor.class)
    @Require({"username","password"})
    public void login(){
        String username = getPara("username");
        String password = getPara("password");
        Customer customer = Customer.getByUsername(username);
        if(customer == null || !customer.getStr("password").equals(password)){
            renderJson(Msg.fail(1,"用户名或密码错误"));
        }else{
            SessionUtil.setCustomer(this,customer);
            renderJson(Msg.success(new CustomerLoginDto(customer)));
        }
    }

    @Require("status:^[1,2]$")
    public void order(){
        Integer cusId = SessionUtil.getCustomerId(this);
        Integer status = getParaToInt("status");
        List<Order> orders = null;
        if(1 == status){
            orders = Order.getServeringOrderByCusId(cusId);
        }else if(2 == status){
            orders = Order.getForwardingOrderByCusId(cusId);
        }
        List<OrderDto> dtoList = new ArrayList<>();
        for(Order order:orders){
            Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
            OrderDto temp = new OrderDto(order,consulter);
            dtoList.add(temp);
        }
        renderJson(Msg.success(dtoList));
    }

    @Require("location:^\\d+$")
    public void list(){
        Integer location = getParaToInt("location");
        List<Customer> customers = Customer.getListByLocation(location);
        List<CustomerDto> dtos = new ArrayList<CustomerDto>();
        for(Customer customer : customers){
            CustomerDto dto = new CustomerDto(customer);
            dtos.add(dto);
        }
        renderJson(Msg.success(dtos));
    }

}
