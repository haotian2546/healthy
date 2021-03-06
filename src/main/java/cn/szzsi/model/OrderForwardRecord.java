package cn.szzsi.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Yishe on 2015-08-18.
 */
public class OrderForwardRecord extends Model<OrderForwardRecord>{
    public static final OrderForwardRecord dao = new OrderForwardRecord();

    public static final OrderForwardRecord createRecord(int order_id,int sender_id,int receiver_id){
        OrderForwardRecord temp = new OrderForwardRecord();
        temp.set("order_id",order_id).set("sender_id",sender_id).set("receiver_id",receiver_id);
        temp.set("create_time",System.currentTimeMillis());
        temp.save();
        return temp;
    }

    public static final List<OrderForwardRecord> getRecordByCusId(int cusId){
        return dao.find("select a.* from order_forward_record a left join consulter_order b on a.order_id=b.id where b.status=2 and (a.sender_id=? or a.receiver_id=?)",cusId,cusId);
    }
}
