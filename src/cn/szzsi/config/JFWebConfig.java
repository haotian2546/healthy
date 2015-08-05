package cn.szzsi.config;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Order;
import com.jfinal.config.*;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class JFWebConfig extends JFinalConfig{
    public void configConstant(Constants me){
        me.setDevMode(true);
        me.setError404View("/404.html");
        me.setError500View("/500.html");
        me.setBaseViewPath("/WEB-INF/views");
    }

    @Override
    public void configRoute(Routes routes){

    }

    public void configPlugin(Plugins me){
        loadPropertyFile("config.properties");
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbc.url"),getProperty("jdbc.username"), getProperty("jdbc.password"));
        me.add(c3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("consulter",Consulter.class);
        arp.addMapping("consulter_order",Order.class);
        arp.addMapping("customer",Customer.class);

    }

    public void configInterceptor(Interceptors me){
        me.add(new SessionInViewInterceptor());
    }

    public void configHandler(Handlers me){

    }
}
