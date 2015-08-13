package cn.szzsi.model;

import cn.szzsi.util.HuanxinUtil;
import com.jfinal.plugin.activerecord.Model;

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
                temp.set("password",password);
                temp.set("nickname",nickname);
                temp.set("location",location);
                temp.set("Integral",0);
                temp.set("online",0);
                temp.set("serviceTimes",0);
                temp.save();
                HuanxinUtil.register(temp);
            }
        }
        if(temp == null){
            temp = getByUsername(username);
        }
        return temp;
    }
}
