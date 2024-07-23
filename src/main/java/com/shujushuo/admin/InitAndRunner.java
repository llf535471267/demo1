package com.shujushuo.admin;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shujushuo.admin.dao.KpiDao;
import com.shujushuo.admin.dao.KpiSqlTemplate;
import com.shujushuo.admin.dao.TestDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 1、当系统启动完毕后执行的方法，一些初始化动作在此进行
 **/
@Slf4j
@Component
public class InitAndRunner implements ApplicationRunner, DisposableBean {

    @Autowired
    private KpiDao kpiDao;

    @Autowired
    private TestDao testDao;

    // 系统启动后执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    // 系统关闭后执行
    @Override
    public void destroy() throws Exception {
    }
}