package cn.szzsi.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.weixin.sdk.api.ApiResult;

/**
 * Created by Yishe on 8/5/2015.
 */
public class Consulter extends Model<Consulter>{
    public static final Consulter dao = new Consulter();

    public static final Consulter getByOpenId(String openid){
        Consulter user = dao.findFirst("select * from consulter where openid=?",openid);
        if(user == null){
            user = regesterByOpenid(openid);
        }
        return user;
    }

    private static final Consulter regesterByOpenid(String openid){
        Consulter user = null;
        synchronized(Consulter.class){
            user = dao.findFirst("select * from consulter where openid=?",openid);
            if(user == null){
                user = new Consulter();
                user.set("username",openid);
                user.set("password",openid);
                user.set("openid",openid);
                user.set("nickname",openid);
                user.set("location",0);
                user.set("childsex",0);
                user.save();
            }
        }
        return user;
    }

    public boolean update(ApiResult apiResult){
        set("nickname",apiResult.getStr("nickname"));
        set("headurl",apiResult.getStr("headimgurl"));
        return update();
    }
}
