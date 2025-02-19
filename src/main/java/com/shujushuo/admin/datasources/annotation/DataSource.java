package com.shujushuo.admin.datasources.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shujushuo.admin.datasources.DataSourceNames;

/**
 * 多数据源注解
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
  String value() default DataSourceNames.ADMIN_SERVICE;
}
