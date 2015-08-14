package cn.szzsi.intercept;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * Created by Yishe on 8/14/2015.
 */
public class CheckInterceptor implements Interceptor{

    @Override
    public void intercept(Invocation in){
        Require require = in.getMethod().getAnnotation(Require.class);
        if(require != null && require.value() != null && require.value().length > 0){
            String[] attrs = require.value();
            for(String attr : attrs){
                if(!Pattern.matches("^[0-9a-z_A-Z]+(:.+)?$",attr)){
                    continue;
                }
                Controller c = in.getController();
                String[] item = attr.split(":",2);
                String value = c.getPara(item[0]);
                if(StringUtils.isBlank(value) || (item.length > 1 && !Pattern.matches(item[1],value))){
                    c.renderError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        }
        in.invoke();
    }
}
