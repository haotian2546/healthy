package cn.szzsi.controller;

import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.Require;
import cn.szzsi.model.Order;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * Created by Yishe on 8/13/2015.
 */
public class WeixinController extends Controller{

    @Clear(AuthInterceptor.class)
    @Require("id:^\\d+$")
    public void evaluation(){
        Integer id = getParaToInt("id");
        setAttr("id",id);
        Order order = Order.dao.findById(id);
        if("POST".equalsIgnoreCase(getRequest().getMethod())) {

        }
    }


}
