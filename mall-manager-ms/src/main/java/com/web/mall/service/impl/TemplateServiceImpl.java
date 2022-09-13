package com.web.mall.service.impl;

import com.web.mall.mapper.TbTypeTemplateMapper;
import com.web.mall.pojo.TbTypeTemplate;
import com.web.mall.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/7 17:19
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    @Override
    public List<TbTypeTemplate> queryAllTemp() {
        return tbTypeTemplateMapper.selectByExample(null);
    }

    @Override
    public int updateOrInsert(TbTypeTemplate tbTypeTemplate) {
        if (tbTypeTemplate.getId() == null || tbTypeTemplate.getId() == 0) {
            return tbTypeTemplateMapper.insert(tbTypeTemplate);
        } else {
            return tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
        }
    }

    @Override
    public int deleteTemp(Long id) {
        return tbTypeTemplateMapper.deleteByPrimaryKey(id);
    }
}
