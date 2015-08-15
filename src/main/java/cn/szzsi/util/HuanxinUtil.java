package cn.szzsi.util;

import cn.szzsi.model.Customer;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.kit.HttpKit;
import com.jfinal.log.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yishe on 8/6/2015.
 */
public class HuanxinUtil{
    private static final Logger logger = Logger.getLogger(HuanxinUtil.class);
    private static final String ORG_NAME = "szzsit";
    private static final String APP_NAME = "ylzx";
    private static final String CLIENT_ID = "YXA6ZP0e0DsKEeWfu2FSEhhxPw";
    private static final String CLIENT_SECRET = "YXA6-Wt_H3GsjkEo1rqCZSaPm3t8GN8";
    private static final String WEB_PRE = "https://a1.easemob.com";


//    private Map<String,String> head = {"Content-Type","application/json"};
    private static AccessToken token;

    private static String getToken(){
        if(token == null || !token.isAvailable()){
            Map<String,String> head = new HashMap<>();
            head.put("Content-Type","application/json");
            String get_token_json = "{\"grant_type\":\"client_credentials\",\"client_id\":\""+CLIENT_ID+"\",\"client_secret\":\""+CLIENT_SECRET+"\"}";
            String data = HttpKit.post(WEB_PRE +String.format("/%s/%s/token",ORG_NAME,APP_NAME),get_token_json,head);
            token = new AccessToken(data);
            if(logger.isDebugEnabled()){
                logger.debug("环信:获取accessToken\n"+data);
            }
        }
        return token.getAccess_token();
    }

    public static void register(Customer customer){
        Map<String,String> head = new HashMap<>();
        head.put("Content-Type","application/json");
        head.put("Authorization","Bearer "+getToken());
        String json = "{\"username\":\""+customer.getStr("username")+"\",\"password\":\""+customer.getStr("chatpd")+"\"}";
        String data = HttpKit.post(WEB_PRE +String.format("/%s/%s/users",ORG_NAME,APP_NAME),json,head);
        if(logger.isDebugEnabled()){
            logger.debug("环信:注册用户\n"+data);
        }
    }

    public static void sendMsg(Customer customer,Message message){
        Map<String,String> head = new HashMap<>();
        head.put("Content-Type","application/json");
        head.put("Authorization","Bearer "+getToken());
        String json = "{\"target_type\":\"users\",\"target\":[\""+customer.getStr("username")+"\"],\"msg\":{\"type\":\"txt\",\"msg\":\""+message.getStr("content")+"\"},\"ext\":{\"order_id\":"+message.getInt("order_id")+"}}";
        String data = HttpKit.post(WEB_PRE +String.format("/%s/%s/messages",ORG_NAME,APP_NAME),json,head);
        if(logger.isDebugEnabled()){
            logger.debug("环信推送:发送\n"+json);
            logger.debug("环信推送:返回\n"+data);
        }

    }

    public static final void noticeNewOrder(Order order){




    }

    public static final void forward(Customer another,Integer id){


    }

    public static final class AccessToken{
        private String access_token;
        private Integer expires_in;
        private String application;
        private Long expiredTime;

        public AccessToken(String jsonstr){
            try{
                Map e = (new ObjectMapper()).readValue(jsonstr,Map.class);
                this.access_token = (String)e.get("access_token");
                this.expires_in = (Integer)e.get("expires_in");
                this.application = (String)e.get("application");
                if(this.expires_in != null) {
                    this.expiredTime = Long.valueOf(System.currentTimeMillis() + (long)((this.expires_in.intValue() - 5) * 1000));
                }
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }

        public String getAccess_token(){
            return access_token;
        }

        public boolean isAvailable() {
            if(expiredTime == null || access_token == null)
                return false;
            return expiredTime.longValue() < System.currentTimeMillis();
        }

        public String getApplication(){
            return application;
        }
    }
}
