package cn.szzsi.model;

import cn.szzsi.util.HuanxinUtil;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Yishe on 8/5/2015.
 */
public class Order extends Model<Order>{
    public static final Order dao = new Order();

    public static final Order getServeringOrderByConId(int consulterId){
        Order order =  dao.findFirst("select * from consulter_order where consulter_id=? and server_status=0",consulterId);
        if(order == null){
            order = createOrder(consulterId);
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

    public Consulter getConsulter(){
        return Consulter.dao.findById(getInt("consulter_id"));
    }

    public Customer getCustomer(){
        Customer customer = null;
        Integer id = getInt("customer_id");
        if(id != null){
            customer = Customer.dao.findById(id);
        }
        return customer;
    }

    public static final List<Order> getUnserverOrder(){
        return dao.find("select * from consulter_order where server_status=0 and status=0");
    }

    public static final List<Order> getServeringOrderByCusId(Integer cusId){
        return dao.find("select * from consulter_order where customer_id=? and server_status=0 and status=1",cusId);
    }

    public static final List<Order> getForwardingOrderByCusId(Integer cusId){
        return dao.find("select * from consulter_order where customer_id=? and server_status=0 and status=2",cusId);
    }
}
