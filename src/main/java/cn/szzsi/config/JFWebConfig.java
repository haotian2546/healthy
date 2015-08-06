package cn.szzsi.config;

import cn.szzsi.controller.CustomerController;
import cn.szzsi.controller.OrderController;
import cn.szzsi.controller.WeixinApiController;
import cn.szzsi.controller.WeixinMsgController;
import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

public class JFWebConfig extends JFinalConfig{
    public void configConstant(Constants me){
        PropKit.use("config.properties");
        me.setDevMode(true);
        me.setError404View("/404.html");
        me.setError500View("/500.html");
        me.setBaseViewPath("/WEB-INF/views");
        // ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    @Override
    public void configRoute(Routes me){
        me.add("/msg", WeixinMsgController.class);
        me.add("/api", WeixinApiController.class);
        me.add("/cus", CustomerController.class);
        me.add("/order", OrderController.class);
    }

    public void configPlugin(Plugins me){
        loadPropertyFile("config.properties");
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbc.url"),PropKit.get("jdbc.username"), PropKit.get("jdbc.password"));
        me.add(c3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("consulter",Consulter.class);
        arp.addMapping("consulter_order",Order.class);
        arp.addMapping("customer",Customer.class);
        arp.addMapping("message",Message.class);

    }

    public void configInterceptor(Interceptors me){
        me.add(new SessionInViewInterceptor());
    }

    public void configHandler(Handlers me){

    }

    public static void main(String[] args) {
        JFinal.start("webapp",80,"/",5);
    }
}
