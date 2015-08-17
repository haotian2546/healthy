package cn.szzsi.model;

import cn.szzsi.dto.IntegralRecordDto;
import cn.szzsi.util.HuanxinUtil;
import cn.szzsi.util.MD5Util;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Yishe on 8/5/2015.
 */
public class Customer extends Model<Customer>{
    public static final Customer dao = new Customer();

    public static final boolean isExist(String username){
        return getByUsername(username) != null;
    }

    public static final Customer getByUsername(String username){
        return dao.findFirst("select * from customer where username=?",username);
    }

    public static final Customer register(String username,String password,String nickname,String headurl,String memo,String department,int location){
        Customer temp = null;
        synchronized(Customer.class){
            if(!isExist(username)){
                temp = new Customer();
                temp.set("username",username);
                temp.set("password",MD5Util.crypt(password,username));
                temp.set("chatpd",MD5Util.crypt(password,username));
                temp.set("nickname",nickname);
                temp.set("location",location);
                temp.set("integral",0);
                temp.set("service_times",0);
                temp.save();
                HuanxinUtil.register(temp);
            }
        }
        if(temp == null){
            temp = getByUsername(username);
        }
        return temp;
    }

    public static final List<Customer> getListByLocation(Integer location){
        return dao.find("select * from customer where location=?",location);
    }

    public void changeIntegral(IntegralRecordDto dto){
        synchronized(this){
            IntegralRecord.createIntegralRecord(this,dto);
            long cur = getLong("integral");
            cur = cur + dto.getPoint();
            set("integral",Long.valueOf(cur)).update();
        }
    }

    public static final Customer getByToken(String token){
        return dao.findFirst("select * from customer where token=?",token);
    }
}
