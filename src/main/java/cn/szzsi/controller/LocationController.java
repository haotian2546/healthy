package cn.szzsi.controller;

import cn.szzsi.dto.LocationDto;
import cn.szzsi.dto.Msg;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Location;
import cn.szzsi.util.SessionUtil;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yishe on 8/14/2015.
 */
public class LocationController extends Controller{

    public void province(){
        List<Location> locations = Location.findProvince();
        List<LocationDto> dtos = new ArrayList<LocationDto>();
        for(Location location:locations){
            LocationDto dto = new LocationDto(location);
            dtos.add(dto);
        }
        renderJson(Msg.success(dtos));
    }

    public void children(){
        Integer code = getParaToInt("code");
        if(code == null){
            Customer cur = SessionUtil.getCustomer(this);
            code =cur.getInt("location");
        }
        List<Location> locations =  Location.children(code);
        List<LocationDto> dtos = new ArrayList<LocationDto>();
        for(Location location:locations){
            LocationDto dto = new LocationDto(location);
            dtos.add(dto);
        }
        renderJson(Msg.success(dtos));
    }

    public void siblings(){
        Integer code = getParaToInt("code");
        if(code == null){
            Customer cur = SessionUtil.getCustomer(this);
            code =cur.getInt("location");
        }
        List<Location> locations =  Location.findSibling(code);
        List<LocationDto> dtos = new ArrayList<LocationDto>();
        for(Location location:locations){
            LocationDto dto = new LocationDto(location);
            dtos.add(dto);
        }
        renderJson(Msg.success(dtos));
    }



}
