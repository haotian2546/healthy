package cn.szzsi.dto;

import cn.szzsi.model.Customer;
import cn.szzsi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yishe on 8/15/2015.
 */
public class CustomerDetailDto extends CustomerDto{

    private String headurl;
    private long integral;
    private String memo;

    public CustomerDetailDto(Customer customer){
        super(customer);
        this.headurl = customer.getStr("headurl");
        this.memo = customer.getStr("memo");
        this.integral = customer.getLong("integral");
    }

    public long getIntegral(){
        return integral;
    }

    public void setIntegral(long integral){
        this.integral = integral;
    }

    public String getMemo(){
        return memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public String getHeadurl(){
        if(StringUtils.isBlank(headurl)){
            return PathUtil.getDefaultHeadUrl();
        }
        return headurl;
    }

    public void setHeadurl(String headurl){
        this.headurl = headurl;
    }

}
