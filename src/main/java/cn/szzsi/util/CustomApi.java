package cn.szzsi.util;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import com.jfinal.kit.HttpKit;
import com.jfinal.log.Logger;
import com.jfinal.weixin.sdk.api.AccessTokenApi;

import java.io.UnsupportedEncodingException;

/**
 * Created by Yishe on 8/7/2015.
 */
public class CustomApi{
    private static final Logger logger = Logger.getLogger(CustomApi.class);

    private static String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    public static final void sendMsg(Consulter consulter,Message message){
        sendMsg(consulter.getStr("openid"),message.getStr("content"));
    }

    private static final void sendMsg(String openId,String message){
        String jsonStr = "{\"touser\":\""+openId+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+message+"\"}}";
        String jsonResult = HttpKit.post(sendUrl + AccessTokenApi.getAccessToken().getAccessToken(),jsonStr);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("哈哈");
        System.out.println(jsonStr);
        try{
            System.out.println(new String(jsonResult.getBytes("UTF-8"),"UTF-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        if(logger.isDebugEnabled()){
            logger.debug("send:\n"+jsonStr);
            logger.debug("\nrecive:\n"+jsonResult);
        }
    }

    public static final void evaluate(Order order){
        String content = "点击 <a href=\\\"www.baidu.com\\\">这里</a> 对本次服务进行评价";
        Consulter consulter = order.getConsulter();
        sendMsg(consulter.getStr("openid"),content);
    }

}
