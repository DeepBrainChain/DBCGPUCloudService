package com.dbc.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageHelp<T> {
        //当前页
        private Integer pageNum;
        //每页的数量
        private Integer pageSize;
        //总共的条数
        private Long total;
        //总共的页数
        private Integer pages;
        //实体类集合
        private List<T> list;
}
