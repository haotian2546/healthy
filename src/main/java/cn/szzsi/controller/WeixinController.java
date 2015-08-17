package cn.szzsi.controller;

import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.Require;
import cn.szzsi.intercept.WeixinInterceptor;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Order;
import cn.szzsi.util.OAuthApi;
import cn.szzsi.util.PathUtil;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Yishe on 8/13/2015.
 */
public class WeixinController extends ApiController{

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

    @Before(WeixinInterceptor.class)
    public void index(){
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
        String openid = OAuthApi.getOpenId(code);
        Consulter consulter = Consulter.getByOpenId(openid);
        if(consulter == null){
            renderError(404);
            return;
        }
        setAttr("consulter",consulter);
    }

    @Before(WeixinInterceptor.class)
    @Require("openid")
    public void edit(){
        String openid = getPara("openid");
        Consulter consulter = Consulter.getByOpenId(openid);
        if(consulter == null){
            renderError(404);
            return;
        }
        if("GET".equalsIgnoreCase(getRequest().getMethod())){
            setAttr("consulter",consulter);
        }else{

        }
    }

}
