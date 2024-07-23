package com.shujushuo.admin.datasources.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.shujushuo.admin.datasources.DataSourceNames;
import com.shujushuo.admin.datasources.DynamicDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 多数据源配置类
 */
@Slf4j
@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.adminserver")
  public DataSource adminserverDataSource() {
    log.info("init adminserver datasource");
    return DataSourceBuilder.create().build();
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.attribution")
  public DataSource attributionDataSource() {
    log.info("init attribution datasource");
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  public SqlSessionFactory adminserverSqlSessionFactory(@Qualifier("adminserverDataSource") DataSource dataSource)
      throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    return factoryBean.getObject();
  }

  @Bean
  public SqlSessionFactory attributionSqlSessionFactory(@Qualifier("adminserverDataSource") DataSource dataSource)
      throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    return factoryBean.getObject();
  }

  @Primary
  @Bean
  public SqlSessionTemplate adminserverSqlSessionTemplate(
      @Qualifier("adminserverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean
  public SqlSessionTemplate attributionSqlSessionTemplate(
      @Qualifier("attributionSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Primary
  @Bean
  public DynamicDataSource dataSource(DataSource adminserverDataSource, DataSource attributionDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceNames.ADMIN_SERVICE, adminserverDataSource);
    targetDataSources.put(DataSourceNames.ATTRIBUTION, attributionDataSource);
    return new DynamicDataSource(adminserverDataSource, targetDataSources);
  }

  @Bean
  public PlatformTransactionManager transactionManager(DynamicDataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}