package cn.szzsi.config;

import cn.szzsi.controller.*;
import cn.szzsi.intercept.AuthInterceptor;
import cn.szzsi.intercept.CheckInterceptor;
import cn.szzsi.model.*;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import freemarker.template.TemplateModelException;

public class JFWebConfig extends JFinalConfig{

    public void configConstant(Constants me){
        PropKit.use("config.properties");
        me.setDevMode(true);
        me.setError404View("/404.html");
        me.setError500View("/500.html");
        me.setBaseViewPath("/WEB-INF/views");
        try{
            FreeMarkerRender.getConfiguration().setSharedVariable("ctx",JFinal.me().getContextPath());
        }catch(TemplateModelException e){
            e.printStackTrace();
        }
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    @Override
    public void configRoute(Routes me){
        me.add("/msg",WeixinMsgController.class);
        me.add("/api",WeixinApiController.class);
        me.add("/cus",CustomerController.class);
        me.add("/order",OrderController.class);
        me.add("/wx",WeixinController.class);
        me.add("/location",LocationController.class);
    }

    public void configPlugin(Plugins me){
        me.add(new EhCachePlugin());
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbc.url"),PropKit.get("jdbc.username"),PropKit.get("jdbc.password"));
        me.add(c3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("consulter",Consulter.class);
        arp.addMapping("consulter_order",Order.class);
        arp.addMapping("customer",Customer.class);
        arp.addMapping("message",Message.class);
        arp.addMapping("integral_record",IntegralRecord.class);
        arp.addMapping("location",Location.class);
        arp.addMapping("evaluation",Evaluation.class);
        arp.addMapping("order_forward_record",OrderForwardRecord.class);
    }

    public void configInterceptor(Interceptors me){
        me.add(new AuthInterceptor());
        me.add(new CheckInterceptor());
        me.add(new SessionInViewInterceptor());
    }

    public void configHandler(Handlers me){
    }

    public static void main(String[] args){
        JFinal.start("webapp",80,"/",5);
    }
}
