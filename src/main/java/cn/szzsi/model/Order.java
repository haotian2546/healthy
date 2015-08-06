package cn.szzsi.model;

import cn.szzsi.hx.HuanxinUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Yishe on 8/5/2015.
 */
public class Order extends Model<Order>{
    public static final Order dao = new Order();

    public static final Order getServeringOrderByConId(int id){
        Order order =  dao.findFirst("select * from consulter_order where consulter_id=? and server_status=0",id);
        if(order == null){
            order = createOrder(id);
        }
        return order;
    }

    private static final Order createOrder(int id){
        Order order = null;
        synchronized(Order.class){
            order = dao.findFirst("select * from consulter_order where consulter_id=? and server_status=0",id);
            if(order == null){
                order = new Order();
                order.set("consulter_id",id);
                order.set("status",0);
                order.set("server_status",0);
                order.set("create_time",System.currentTimeMillis());
                order.save();
                HuanxinUtil.noticeNewOrder(order);
            }
        }

        return order;
    }

    public Customer getCustomer(){
        Customer customer = null;
        Integer id = getInt("customer_id");
        if(id != null){
            customer = Customer.dao.findById(id);
        }
        return customer;
    }
}
