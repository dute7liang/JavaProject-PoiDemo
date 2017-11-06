package com.app.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记为excel 创建实体忽略,放置死循环的造成
 * @author hqb qb.huang@wescxx.com
 * @date 2015年10月13日 上午11:53:36 
 * @version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelIgnore {

}
