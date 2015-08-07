package cn.szzsi.util;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import com.jfinal.core.Controller;

/**
 * Created by Yishe on 8/7/2015.
 */
public class SessionUtil{
    private static final String CUS_ATTR_NAME = "_cus_attr_name_";

    public static final Integer getCustomerId(Controller c){
        Customer customer = getCustomer(c);
        if(customer != null)
            return customer.getInt("id");
        return null;
    }

    public static final Customer getCustomer(Controller c){
        return c.getSessionAttr(CUS_ATTR_NAME);
    }

    public static final void setCustomer(Controller c,Customer customer){
        c.setSessionAttr(CUS_ATTR_NAME,customer);
    }


}
