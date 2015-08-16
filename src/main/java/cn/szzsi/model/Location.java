package cn.szzsi.model;

import cn.szzsi.util.LocationUtil;
import com.jfinal.plugin.activerecord.Model;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Yishe on 8/14/2015.
 */
public class Location extends Model<Location>{

    public static final Location dao = new Location();


    public static final Location getByCode(int code){
        return dao.findFirst("select * from location where code=?",code);
    }

    public static final List<Location> findProvince(){
        return dao.find("select * from location where code%10000=0");
    }

    public static final List<Location> findCity(int code){
        String pro = String.valueOf(code).replace("\\d{4}$","");
        int min = Integer.valueOf(pro+"0000");
        int max = Integer.valueOf(pro+"9999");
        return dao.find("select * from location where code>? and code<? and code%100=0",min,max);
    }

    public static final List<Location> findTown(int code){
        String city = String.valueOf(code).replace("\\d{2}$","");
        int min = Integer.valueOf(city+"00");
        int max = Integer.valueOf(city+"99");
        return dao.find("select * from location where code>? and code<?",min,max);
    }

    public static final List<Location> findSibling(int code){
        if(!Pattern.matches("\\d{6}",String.valueOf(code))){
            return Collections.EMPTY_LIST;
        }
        if(LocationUtil.isProvinceLevel(code)){
            return findProvince();
        }else if(LocationUtil.isCityLevel(code)){
            return findCity(code);
        }else{
            return findTown(code);
        }
    }

    public static final List<Location> children(int code){
        if(!Pattern.matches("\\d{6}",String.valueOf(code))){
            return findProvince();
        }
        if(LocationUtil.isProvinceLevel(code)){
            return findCity(code);
        }else{
            return findTown(code);
        }
    }
}
