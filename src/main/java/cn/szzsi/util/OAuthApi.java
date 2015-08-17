package cn.szzsi.util;

import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Yishe on 8/17/2015.
 */
public class OAuthApi{

    private static final String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect";

    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    public static String getRedirectUrl(String url,String state) throws UnsupportedEncodingException{
        ApiConfig config = ApiConfigKit.getApiConfig();
        return String.format(GET_CODE_URL,config.getAppId(),URLEncoder.encode(url,"UTF-8"),state);
    }

    public static String getOpenId(String code) {
        ApiConfig config = ApiConfigKit.getApiConfig();
        String rdata = HttpKit.get(String.format(GET_ACCESS_TOKEN_URL,config.getAppId(),config.getAppSecret(),code));
        ApiResult result = new ApiResult(rdata);
        return result.getStr("openid");
    }

    public static void main(String[] args) throws UnsupportedEncodingException{
        System.out.println(URLEncoder.encode("http://www.baidu.com?id=3","UTF-8"));
    }


}
