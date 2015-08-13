package cn.szzsi.controller;

import cn.szzsi.dto.Msg;
import cn.szzsi.dto.OrderDto;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import cn.szzsi.util.CustomApi;
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

    public void detail(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        Consulter consulter = Consulter.dao.findById(order.getInt("consulter_id"));
        OrderDto temp = new OrderDto(order,consulter);
        renderJson(Msg.success(temp));
    }

    public void confirm(){
        Integer id = getParaToInt("id");
        Order order = Order.dao.findById(id);
        if(order.getInt("customer_id") == null){
            order.set("customer_id",SessionUtil.getCustomerId(this));
            order.set("server_status",1).update();
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"来晚了一步"));
        }
    }

    public void chat(){
        Integer id = getParaToInt("id");
        String content = getPara("content");
        Order order = Order.dao.findById(id);
        Integer customerId = SessionUtil.getCustomerId(this);
        if(customerId.equals(order.getInt("customer_id"))){
            Message message = Message.createCustomerMessage(order,content);
            Consulter consulter = order.getConsulter();
            CustomApi.sendMsg(consulter,message);
            renderJson(Msg.SUCCESS);
        }else{
            renderJson(Msg.fail(1,"参数错误"));
        }
    }

    public void history(){
        Integer id = getParaToInt("id");
        Integer pageSize = getParaToInt("pageSize");
        Integer pageNo = getParaToInt("pageNo");
        Long time = getParaToLong("time");
        Page<Message> page = Message.getPage(pageNo,pageSize,id,time);
        renderJson(Msg.success(page));
    }

    public void evaluation(){


    }

    public void consulter(){
        if("GET".equalsIgnoreCase(getRequest().getMethod())) {

        } else {


        }

    }

    public void forward(){


    }







}
