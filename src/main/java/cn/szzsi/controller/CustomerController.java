package cn.szzsi.controller;

import cn.szzsi.dto.*;
import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.Require;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Order;
import cn.szzsi.model.OrderForwardRecord;
import cn.szzsi.util.MD5Util;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import org.apache.commons.lang3.StringUtils;

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
        if(customer != null && ( customer.getStr("password").equals(MD5Util.crypt(password,username)) || customer.getStr("password").equals(password) )){
            SessionUtil.loginCustomer(customer);
            renderJson(Msg.success(new CustomerLoginDto(customer)));
        }else{
            renderJson(Msg.fail(1,"用户名或密码错误"));
        }
    }

    public void detail(){
        Customer customer = SessionUtil.getCustomer(this);
        if("GET".equalsIgnoreCase(getRequest().getMethod())) {
            CustomerDetailDto dto = new CustomerDetailDto(customer);
            renderJson(Msg.success(dto));
        }else{
            String nickname = getPara("nickname");
            if(StringUtils.isNotBlank(nickname)){
                customer.set("nickname",nickname);
            }
            String headurl = getPara("headurl");
            if(StringUtils.isNotBlank(headurl)){
                customer.set("headurl",headurl);
            }
            Integer location = getParaToInt("location");
            if(location != null){
                customer.set("location",location);
            }
            String company = getPara("company");
            if(StringUtils.isNotBlank(company)){
                customer.set("company",company);
            }
            String department = getPara("department");
            if(StringUtils.isNotBlank(department)){
                customer.set("department",department);
            }
            String memo = getPara("memo");
            if(StringUtils.isNotBlank(memo)){
                customer.set("memo",memo);
            }
            customer.update();
            renderJson(Msg.SUCCESS);
        }

    }


    @Require("password")
    public void changepd(){
        String pwd = getPara("password");
        Customer cur = SessionUtil.getCustomer(this);
        cur.set("password",MD5Util.crypt(pwd,cur.getStr("username"))).update();
        renderJson(Msg.SUCCESS);
    }

    public void logout(){
        Customer customer = SessionUtil.getCustomer(this);
        SessionUtil.logoutCustomer(customer);
        getSession().invalidate();
        renderJson(Msg.SUCCESS);
    }

//    @Require("status:^[1,2]$")
//    public void order(){
//        Integer cusId = SessionUtil.getCustomerId(this);
//        Integer status = getParaToInt("status");
//        List<Order> orders = null;
//        if(1 == status){
//            orders = Order.getServeringOrderByCusId(cusId);
//        }else if(2 == status){
//            orders = Order.getForwardingOrderByCusId(cusId);
//        }
//        List<OrderDto> dtoList = new ArrayList<>();
//        for(Order order : orders){
//            Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
//            OrderDto temp = new OrderDto(order,consulter);
//            dtoList.add(temp);
//        }
//        renderJson(Msg.success(dtoList));
//    }

    public void servering(){
        Integer cusId = SessionUtil.getCustomerId(this);
        List<Order> orders = Order.getServeringOrderByCusId(cusId);
        List<OrderDto> dtoList = new ArrayList<>();
        for(Order order : orders){
            Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
            OrderDto temp = new OrderDto(order,consulter);
            dtoList.add(temp);
        }
        renderJson(Msg.success(dtoList));
    }

    public void forwarding(){
        Integer cusId = SessionUtil.getCustomerId(this);
        List<OrderForwardRecord> records = OrderForwardRecord.getRecordByCusId(cusId);
        List<OrderForwardDto> dtos = new ArrayList<OrderForwardDto>();
        for(OrderForwardRecord record:records){
            Order order = Order.dao.findById(record.getInt("order_id"));
            if(order == null || order.getInt("status") != 2){
                continue;
            }
            Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
            Customer sender = Customer.dao.findById(record.getInt("sender_id"));
            Customer receiver = Customer.dao.findById(record.getInt("receiver_id"));
            OrderForwardDto dto = new OrderForwardDto(cusId,order,consulter,sender,receiver);
            dtos.add(dto);
        }
        renderJson(Msg.success(dtos));
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
