package com.web.mall.service;

import com.web.mall.pojo.TbTypeTemplate;

import java.util.List;

public interface TemplateService {

    public List<TbTypeTemplate> queryAllTemp();

    public int updateOrInsert(TbTypeTemplate tbTypeTemplate);


    public int deleteTemp(Long id);
}
