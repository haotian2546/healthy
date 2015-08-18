package cn.szzsi.dto;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Order;

/**
 * Created by Yishe on 2015-08-18.
 */
public class OrderForwardDto extends OrderDto{
    private int type;
    private CustomerDto sender;
    private CustomerDto receiver;

    public OrderForwardDto(Integer cusId,Order order,Consulter consulter,Customer sender,Customer receiver){
        super(order,consulter);
        this.type = cusId.intValue() == sender.getInt("id").intValue() ? 0: 1;
        this.sender = new CustomerDto(sender);
        this.receiver = new CustomerDto(receiver);
    }

    public CustomerDto getSender(){
        return sender;
    }

    public void setSender(CustomerDto sender){
        this.sender = sender;
    }

    public CustomerDto getReceiver(){
        return receiver;
    }

    public void setReceiver(CustomerDto receiver){
        this.receiver = receiver;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }
}
