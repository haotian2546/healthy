package cn.szzsi.dto;

import cn.szzsi.model.Order;

/**
 * Created by Yishe on 8/14/2015.
 */
public class IntegralRecordDto{
    private int point;
    private String memo;

    public IntegralRecordDto(Order order){
        long start = order.getLong("create_time");
        long end = System.currentTimeMillis();
        long deta = end - start;
        if(deta <= 1*60*1000){
            point = 5;
            memo = "首次响应在1分钟之内";
        }else if(deta <= 2*60*1000){
            point = 4;
            memo = "首次响应在2分钟之内";
        }else if(deta <= 3*60*1000){
            point = 4;
            memo = "首次响应在2分钟之内";
        }


    }

    public int getPoint(){
        return point;
    }

    public void setPoint(int point){
        this.point = point;
    }

    public String getMemo(){
        return memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }
}
