package com.shujushuo.admin.dao;

import lombok.Data;

/** 首页KPI数据的sql模板表(t_kpi_sql_template) **/
@Data
public class KpiSqlTemplate {

	private long id;// 自增ID

	private String name;// kpi名称

	private String sql_template;// sql语句

	private String tips;// 说明提示

	private int def;// 是否默认显示 0为否 1为是

	private long create_time;// 项目创建时间戳，不可修改

	private long update_time;// 项目最后修改时间戳
}