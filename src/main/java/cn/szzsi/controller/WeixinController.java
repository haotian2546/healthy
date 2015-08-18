package cn.szzsi.controller;

import cn.szzsi.dto.ConsulterDto;
import cn.szzsi.dto.Msg;
import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.Require;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Order;
import cn.szzsi.util.OAuthApi;
import cn.szzsi.util.PathUtil;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.weixin.sdk.jfinal.ApiInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Yishe on 8/13/2015.
 */
//@Before(WeixinInterceptor.class)
public class WeixinController extends ApiController{

    private static final String OPENID_KEY = "_openid_";

    public ApiConfig getApiConfig(){
        return SessionUtil.getApiConfig(this);
    }

    @Clear(AuthInterceptor.class)
    @Require("id:^\\d+$")
    public void evaluation(){
        Integer id = getParaToInt("id");
        setAttr("id",id);
        Order order = Order.dao.findById(id);
        if("POST".equalsIgnoreCase(getRequest().getMethod())){


        }
    }

    @Clear(AuthInterceptor.class)
    public void course(){

    }

    @Clear(AuthInterceptor.class)
    @Before(ApiInterceptor.class)
    public void index(){
        String openid = getSessionAttr(OPENID_KEY);
        if(StringUtils.isBlank(openid)){
            String code = getPara("code");
            if(StringUtils.isBlank(code)){
                String cur = PathUtil.getPath("/wx/index");
                try{
                    String url = OAuthApi.getRedirectUrl(cur,"");
                    redirect(url);
                }catch(UnsupportedEncodingException e){
                    renderError(404);
                }
                return;
            }
            openid = OAuthApi.getOpenId(code);
            setSessionAttr(OPENID_KEY,openid);
        }
        Consulter consulter = Consulter.getByOpenId(openid);
        if(consulter == null){
            renderError(404);
            return;
        }
        setAttr("consulter",new ConsulterDto(consulter));
    }

    @Clear(AuthInterceptor.class)
    @Require("openid")
    public void edit(){
        String openid = getPara("openid");
        Consulter consulter = Consulter.getByOpenId(openid);
        if(consulter == null){
            renderError(404);
            return;
        }
        if("GET".equalsIgnoreCase(getRequest().getMethod())){
            setAttr("consulter",new ConsulterDto(consulter));
        }else{
            Integer location = getParaToInt("location");
            if(location != null){
                consulter.set("location",location);
            }
            String address = getPara("address");
            if(address != null){
                consulter.set("address",address);
            }
            String phone = getPara("phone");
            if(address != null){
                consulter.set("phone",phone);
            }
            Integer childsex = getParaToInt("childsex");
            if(childsex != null){
                consulter.set("childsex",childsex);
            }
            String childname = getPara("childname");
            if(childname != null){
                consulter.set("childname",childname);
            }
            Long childbirth = getParaToLong("childbirth");
            if(childbirth != null){
                consulter.set("childbirth",childbirth);
            }
            consulter.update();
            renderJson(Msg.SUCCESS);
        }
    }

}
