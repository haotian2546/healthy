package cn.szzsi.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.weixin.sdk.api.ApiResult;

/**
 * Created by Yishe on 8/5/2015.
 */
public class Consulter extends Model<Consulter>{
    public static final Consulter dao = new Consulter();

    public static final Consulter getByOpenId(String openid){
        return dao.findFirst("select * from consulter where openid=?",openid);
    }

    public static final Consulter regesterByOpenid(String openid){
        Consulter user = null;
        synchronized(Consulter.class){
            user = dao.findFirst("select * from consulter where openid=?",openid);
            if(user == null){
                user = new Consulter();
                user.set("username",openid).set("password",openid).set("openid",openid).set("nickname",openid);
                user.set("location",0).set("childsex",0).save();
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
