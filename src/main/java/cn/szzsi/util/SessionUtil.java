package cn.szzsi.util;

import cn.szzsi.model.Customer;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * Created by Yishe on 8/7/2015.
 */
public class SessionUtil{
    private static ApiConfig CONFIG;

    private static final String ONLINE_CACHE_NAME = "_online_cache_";

    public static final ApiConfig getApiConfig(Controller c){
        if(CONFIG == null){
            ApiConfig ac = new ApiConfig();
            ac.setToken(PropKit.get("token"));
            ac.setAppId(PropKit.get("appId"));
            ac.setAppSecret(PropKit.get("appSecret"));
            ac.setEncryptMessage(PropKit.getBoolean("encryptMessage",false));
            ac.setEncodingAesKey(PropKit.get("encodingAesKey","setting it in config file"));
            CONFIG = ac;
        }
        return CONFIG;
    }

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
        Customer customer = CacheKit.get(ONLINE_CACHE_NAME,token);
        if(customer == null){
            customer = Customer.getByToken(token);
            if(customer != null){
                CacheKit.put(ONLINE_CACHE_NAME,token,customer);
            }
        }
        return customer;
    }

    public static final void loginCustomer(Customer customer){
        String token = customer.getStr("token");
        if(StringUtils.isNoneBlank(token)){
            Customer temp = CacheKit.get(ONLINE_CACHE_NAME,token);
            if(temp != null){
                CacheKit.remove(ONLINE_CACHE_NAME,token);
            }
        }
        token = getToken(customer.getStr("username"),customer.getStr("password"));
        customer.set("token",token).update();
        CacheKit.put(ONLINE_CACHE_NAME,token,customer);
    }

    public static final void logoutCustomer(Customer customer){
        String token = customer.getStr("token");
        customer.set("token",null).update();
        if(StringUtils.isNoneBlank(token)){
            Customer temp = CacheKit.get(ONLINE_CACHE_NAME,token);
            if(temp != null){
                CacheKit.remove(ONLINE_CACHE_NAME,token);
            }
        }
    }

    public static final boolean isOnline(Integer cusId){
        Customer customer = Customer.dao.findById(cusId);
        if(customer == null){
            return false;
        }
        String token = customer.getStr("token");
        if(StringUtils.isBlank(token)){
            return false;
        }
        Object obj = CacheKit.get(ONLINE_CACHE_NAME,token);
        return obj != null;
    }

    public static final boolean isLogin(Controller c){
        return getCustomer(c) != null;
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
