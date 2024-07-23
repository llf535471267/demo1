package com.shujushuo.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shujushuo.admin.datasources.annotation.DataSource;

@Mapper
@DataSource
public interface KpiDao {

    /** 所有kpi模板 **/
    @Select("SELECT * FROM t_kpi_sql_template")
    public List<KpiSqlTemplate> get_all();
}