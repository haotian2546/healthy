package cn.szzsi.intercept;

import java.lang.annotation.*;

/**
 * Created by Yishe on 8/14/2015.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Require{
    public String[] value();
}
