package cn.szzsi.intercept;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yishe on 8/17/2015.
 */
public class WeixinInterceptor implements Interceptor{

    @Override
    public void intercept(Invocation in){
        Controller c = in.getController();
        HttpServletRequest request = c.getRequest();
        String agent = request.getHeader("user-agent");
        if(StringUtils.isNotBlank(agent) && agent.matches(".+MicroMessenger.+")){
            in.invoke();
        }else{
            c.renderError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
