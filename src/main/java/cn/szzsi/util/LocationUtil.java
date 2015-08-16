package cn.szzsi.util;

import java.util.regex.Pattern;

/**
 * Created by Yishe on 8/16/2015.
 */
public class LocationUtil{
    public static final boolean isProvinceLevel(int code){
        return Pattern.matches("\\d{2}0000",String.valueOf(code));
    }

    public static final boolean isCityLevel(int code){
        return Pattern.matches("\\d{4}00",String.valueOf(code));
    }

}
