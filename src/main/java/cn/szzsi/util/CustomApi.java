package cn.szzsi.util;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Message;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.AccessTokenApi;

/**
 * Created by Yishe on 8/7/2015.
 */
public class CustomApi{
    private static String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    public static final void sendMsg(Consulter consulter,Message message){
        String jsonStr = "{\"touser\":\""+consulter.get("openid")+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+message.get("content")+"\"}}";
        String jsonResult = HttpKit.post(sendUrl + AccessTokenApi.getAccessToken().getAccessToken(),jsonStr);
        
    }

}
