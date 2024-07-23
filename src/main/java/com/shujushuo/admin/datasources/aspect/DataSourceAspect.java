package com.shujushuo.admin.datasources.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.shujushuo.admin.datasources.DataSourceNames;
import com.shujushuo.admin.datasources.DynamicDataSource;
import com.shujushuo.admin.datasources.annotation.DataSource;

import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * 多数据源，切面处理类
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

  // @Pointcut("@within(dataSource) ||
  // @annotation(com.shujushuo.admin.datasources.annotation.DataSource)")
  // public void dataSourcePointCut() {

  // }

  // @Around("dataSourcePointCut()")
  // public Object around(ProceedingJoinPoint point) throws Throwable {
  // Tuple2<String, DataSource> data = get_method_data(point);
  // String method = data.getT1();
  // DataSource ds = data.getT2();
  // if (ds == null) {
  // DynamicDataSource.setDataSource(DataSourceNames.ADMIN_SERVICE);
  // } else {
  // DynamicDataSource.setDataSource(ds.value());
  // }
  // log.info("{}使用数据源{}", method, DynamicDataSource.getDataSource());
  // try {
  // return point.proceed();
  // } finally {
  // DynamicDataSource.clearDataSource();
  // }
  // }

  // 切点表达式逻辑：类使用了@DataSource注解，触发这个前置方法
  @Before("@within(dataSource)")
  public void setDataSource(JoinPoint point, DataSource dataSource) throws Throwable {
    Tuple2<String, String> data = get_method_data_source(point);
    String method = data.getT1();
    String ds = data.getT2();
    DynamicDataSource.setDataSource(ds);
    log.info("{}使用数据源{}", method, ds);
  }

  @After("@within(dataSource)")
  public void clearDataSource(JoinPoint point, DataSource dataSource) {
    String method = get_method(point);
    log.info("{}清空数据源{}", method, DynamicDataSource.getDataSource());
    DynamicDataSource.clearDataSource();
  }

  // 获取方法名称和DataSource
  private Tuple2<String, String> get_method_data_source(JoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    String m = method.getDeclaringClass().getName() + "." + method.getName();
    Class<?> targetClass = method.getDeclaringClass();
    DataSource dataSource = targetClass.getAnnotation(DataSource.class);
    String ds = null;
    if (dataSource == null) {
      ds = DataSourceNames.ADMIN_SERVICE;
    } else {
      ds = dataSource.value();
    }
    return Tuples.of(m, ds);
  }

  // 获取方法名称
  private String get_method(JoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    String m = method.getDeclaringClass().getName() + "." + method.getName();
    return m;
  }
}
