package cn.szzsi.model;

import cn.szzsi.dto.IntegralRecordDto;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Yishe on 8/14/2015.
 */
public class IntegralRecord extends Model<IntegralRecord>{

    public static final IntegralRecord dao = new IntegralRecord();

    public static final IntegralRecord createIntegralRecord(Customer customer,IntegralRecordDto dto){
        IntegralRecord temp = new IntegralRecord();
        temp.set("customer_id",customer.getInt("id"));
        temp.set("create_time",System.currentTimeMillis());
        temp.set("addpoint",dto.getPoint());
        temp.set("memo",dto.getMemo());
        temp.save();
        return temp;
    }


}
