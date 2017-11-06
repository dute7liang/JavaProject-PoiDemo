package com.app.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义的基础注解
 * @author zhengyuning
 * @date 2017年3月27日 上午10:02:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BasicAnn {

    /**
     * 列名
     */
    public String name() default "";
    
    /**
     * 列名排序
     */
    public int orderNum() default 1;

    /**
     * Label-Value分隔符
     */
    public String separator() default "";
}
