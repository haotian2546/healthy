package cn.szzsi.controller;

import cn.szzsi.dto.ConsulterDto;
import cn.szzsi.dto.IntegralRecordDto;
import cn.szzsi.dto.Msg;
import cn.szzsi.dto.OrderDto;
import cn.szzsi.intercept.Require;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import cn.szzsi.util.CustomApi;
import cn.szzsi.util.HuanxinUtil;
import cn.szzsi.util.SessionUtil;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.ApiController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yishe on 8/6/2015.
 */
public class OrderController extends ApiController{
    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Override
    public ApiConfig getApiConfig(){
        return SessionUtil.getApiConfig(this);
    }

    /**
     * 获取大厅未服务订单
     */
    public void unserver(){
        List<Order> orderList = Order.getUnserverOrder();
        List<OrderDto> dtoList = new ArrayList<>();
        for(Order order:orderList){
            Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
            OrderDto temp = new OrderDto(order,consulter);
            dtoList.add(temp);
        }
        renderJson(Msg.success(dtoList));
    }

    @Require("id:^\\d+$")
    public void detail(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
        OrderDto temp = new OrderDto(order,consulter);
        renderJson(Msg.success(temp));
    }

    @Require("id:^\\d+$")
    public void confirm(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        if(order.getInt("customer_id") == null || order.getInt("status") == 2){
            order.set("customer_id",SessionUtil.getCustomerId(this));
            order.set("status",1).update();
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"来晚了一步"));
        }
    }

    @Require({"id:^\\d+$","content"})
    public void chat(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        String content = getPara("content");
        Integer customerId = SessionUtil.getCustomerId(this);
        if(customerId.equals(order.getInt("customer_id"))){
            Message message = Message.createCustomerMessage(order,content);
            if(order.isFirstAck()){
                Customer customer = order.getCustomer();
                customer.changeIntegral(new IntegralRecordDto(order));
                order.set("ack_time",System.currentTimeMillis()).update();
            }
            Consulter consulter = order.getConsulter();
            CustomApi.sendMsg(consulter,message);
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"参数错误"));
        }
    }

    @Require("id:^\\d+$")
    public void history(){
        Integer id = getParaToInt("id");
        Integer pageSize = getParaToInt("pageSize");
        Integer pageNo = getParaToInt("pageNo");
        Long time = getParaToLong("time");
        Page<Message> page = Message.getPage(pageNo,pageSize,id,time);
        renderJson(Msg.success(page));
    }

    @Require("id:^\\d+$")
    public void evaluation(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        Integer customerId = SessionUtil.getCustomerId(this);
        if(customerId.equals(order.getInt("customer_id")) && order.getInt("server_status") == 0){
            CustomApi.evaluate(order);
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"参数错误"));
        }
    }

    @Require("id:^\\d+$")
    public void consulter(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        Consulter consulter = order.getConsulter();
        if("GET".equalsIgnoreCase(getRequest().getMethod())) {
            ConsulterDto dto = new ConsulterDto(consulter);
            renderJson(Msg.success(dto));
        } else {
            int location = getParaToInt("location");
            int childsex = getParaToInt("childsex");
            String childname = getPara("childname");
            String phone = getPara("phone");
            long childbirth = getParaToLong("childbirth");
            consulter.set("location",location).set("childsex",childsex).set("childname",childname);
            consulter.set("childbirth",childbirth).set("phone",phone);
            consulter.update();
            renderJson(Msg.SUCCESS);
        }
    }

    @Require({"id:^\\d+$","customer_id:^\\d+$"})
    public void forward(){
        Integer id = getParaToInt("id");
        Integer customer_id = getParaToInt("customer_id");
        Customer another = Customer.dao.findById(customer_id);
        if(another == null){
            renderJson(Msg.fail(1,"参数错误"));
            return;
        }
        Order order = Order.dao.findById(id);
        Integer customerId = SessionUtil.getCustomerId(this);
        if(customerId.equals(order.getInt("customer_id"))){
            order.forward(another);
            HuanxinUtil.forward(another,id);
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"参数错误"));
        }
    }


}
