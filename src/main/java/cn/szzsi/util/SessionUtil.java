package cn.szzsi.util;

import cn.szzsi.model.Customer;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yishe on 8/7/2015.
 */
public class SessionUtil implements HttpSessionListener{
    private static final String CUS_ATTR_NAME = "_cus_attr_name_";

    private static final Map<String,Customer> map = new ConcurrentHashMap<String,Customer>();

    private static final Set<Integer> online = new TreeSet<Integer>();

    public static final Integer getCustomerId(Controller c){
        Customer customer = getCustomer(c);
        if(customer != null)
            return customer.getInt("id");
        return null;
    }

    public static final Customer getCustomer(Controller c){
        String token = c.getRequest().getHeader("token");
        if(StringUtils.isBlank(token)){
            return null;
        }
        Customer customer = map.get(token);
        if(customer == null){
            customer = Customer.getByToken(token);
            if(customer != null){
                map.put(token,customer);
            }
        }
        return customer;
    }

    public static final void loginCustomer(Customer customer){
        String token = getToken(customer.getStr("username"),customer.getStr("password"));
        customer.set("token",token).update();
        map.put(token,customer);
    }

    public static final void logoutCustomer(Customer customer){
        String token = customer.getStr("token");
        customer.set("token",null).update();
        map.remove(token);
    }

    public static final void setOnline(Controller c){
        Integer cid = getCustomerId(c);
        c.setSessionAttr(CUS_ATTR_NAME,cid);
        online.add(cid);
    }

    public static final boolean isOnline(Integer conId){
        return online.contains(conId);
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
        Integer cid = (Integer) se.getSession().getAttribute(CUS_ATTR_NAME);
        if(cid != null){
            online.remove(cid);
        }
    }

    public static String getToken(String username,String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(username.getBytes("UTF-8"));
            md.update(password.getBytes("UTF-8"));
            md.update(String.valueOf(System.currentTimeMillis()).getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for(int i = 0;i < digest.length;++i){
                shaHex = Integer.toHexString(digest[i] & 255);
                if(shaHex.length() < 2){
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        }catch(Exception var12){
            return "asdaskjfaosfdmlsaijcalsdmwlkdjsac";
        }
    }

}
