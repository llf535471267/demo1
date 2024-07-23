package com.shujushuo.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shujushuo.admin.dao.KpiDao;
import com.shujushuo.admin.dao.KpiSqlTemplate;
import com.shujushuo.admin.dao.TestDao;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private KpiDao kpiDao;

    @Autowired
    private TestDao testDao;

    @GetMapping("/test1")
    public List<KpiSqlTemplate> test1() {
        List<KpiSqlTemplate> list1 = kpiDao.get_all();
        return list1;
    }

    @GetMapping("/test2")
    public List<KpiSqlTemplate> test2() {
        List<KpiSqlTemplate> list2 = testDao.get_all();
        return list2;
    }
}