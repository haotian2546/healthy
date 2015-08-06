package cn.szzsi.dto;

/**
 * Created by Yishe on 8/6/2015.
 */
public class Msg{

    public static final Msg SUCCESS = new Msg(true,0);

    private boolean success;
    private int code;
    private String desc;

    public Msg(boolean success,int code){
        this.success = success;
        this.code = code;
    }

    public Msg(boolean success,int code,String desc){
        this.success = success;
        this.code = code;
        this.desc = desc;
    }

    public static final Msg success(String desc){
        return new Msg(true,0,desc);
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }
}
