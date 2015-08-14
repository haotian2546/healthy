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
            point = 3;
            memo = "首次响应在3分钟之内";
        }else if(deta <= 4*60*1000){
            point = 2;
            memo = "首次响应在4分钟之内";
        }else if(deta <= 5*60*1000){
            point = 1;
            memo = "首次响应在5分钟之内";
        }else if(deta <= 6*60*1000){
            point = -2;
            memo = "首次响应在6分钟之内";
        }else if(deta <= 7*60*1000){
            point = -4;
            memo = "首次响应在7分钟之内";
        }else if(deta <= 8*60*1000){
            point = -6;
            memo = "首次响应在8分钟之内";
        }else if(deta <= 9*60*1000){
            point = -8;
            memo = "首次响应在9分钟之内";
        }else if(deta <= 10*60*1000){
            point = -10;
            memo = "首次响应在10分钟之内";
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
