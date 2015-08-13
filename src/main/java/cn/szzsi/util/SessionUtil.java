package cn.szzsi.util;

import cn.szzsi.model.Customer;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Yishe on 8/7/2015.
 */
public class SessionUtil implements HttpSessionListener {
    private static final String CUS_ATTR_NAME = "_cus_attr_name_";

    public static final Integer getCustomerId(Controller c){
        Customer customer = getCustomer(c);
        if(customer != null)
            return customer.getInt("id");
        return null;
    }

    private static final Set<Integer> isOnline = new TreeSet<>();

    public static final Customer getCustomer(Controller c){
        return c.getSessionAttr(CUS_ATTR_NAME);
    }

    public static final void setCustomer(Controller c,Customer customer){
        isOnline.add(customer.getInt("id"));
        c.setSessionAttr(CUS_ATTR_NAME,customer);
    }

    public static final boolean isOnline(Integer conId){
        return isOnline.contains(conId);
    }

    public static final boolean isLogin(Controller c){
        return getCustomer(c) != null;
    }

    public static final ApiConfig getApiConfig(Controller c){
        ApiConfig ac = new ApiConfig();
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage",false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey","setting it in config file"));
        return ac;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se){
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se){
        Customer customer = (Customer) se.getSession().getAttribute(CUS_ATTR_NAME);
        if(customer != null){
            isOnline.remove(customer.getInt("id"));
        }
    }

}
