package cn.szzsi.dto;

/**
 * Created by Yishe on 8/6/2015.
 */
public class Msg{

    public static final Msg SUCCESS = new Msg(true,0);

    private boolean success;
    private int code;
    private String desc;
    private Object data;

    public Msg(boolean success,int code){
        this(success,code,null);
    }

    public Msg(boolean success,int code,String desc){
        this(success,code,desc,null);
    }

    public Msg(boolean success,int code,String desc,Object data){
        this.success = success;
        this.code = code;
        this.desc = desc;
        this.data = data;
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

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public static final Msg success(Object data){
        return new Msg(true,0,null,data);
    }

    public static final Msg fail(int code,String desc){
        return new Msg(false,code,desc);
    }
}
