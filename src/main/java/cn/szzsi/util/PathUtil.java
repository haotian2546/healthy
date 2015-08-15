package cn.szzsi.util;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;

/**
 * Created by Yishe on 8/15/2015.
 */
public class PathUtil{
    public static final String getPath(String path){
        return PropKit.get("web.url") + JFinal.me().getContextPath() + path;
    }

    public static final String getDefaultHeadUrl(){
        return getPath("/res/images/default_head.jpg");
    }
}
