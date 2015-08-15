package cn.szzsi.intercept;

import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yishe on 8/8/2015.
 */
public class AuthInterceptor implements Interceptor{
    @Override
    public void intercept(Invocation ai){
        Controller c = ai.getController();
        if(SessionUtil.isLogin(c)){
            SessionUtil.setOnline(c);
            ai.invoke();
        }else{
            c.renderError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
