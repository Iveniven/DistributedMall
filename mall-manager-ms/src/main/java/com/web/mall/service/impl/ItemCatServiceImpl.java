package com.web.mall.service.impl;

import com.web.mall.mapper.TbItemCatMapper;
import com.web.mall.mapper.TbTypeTemplateMapper;
import com.web.mall.pojo.TbItemCat;
import com.web.mall.pojo.TbItemCatExample;
import com.web.mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/9 10:03
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    @Override
    public List<TbItemCat> queryItemsByParentId(Long id) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        for (TbItemCat t:list){
            t.setTbTypeTemplate(tbTypeTemplateMapper.selectByPrimaryKey(t.getTypeId()));
        }
        return list;
    }

    @Override
    public int updateOrInsert(TbItemCat tbItemCat) {
        if(tbItemCat.getId()==null){
            return tbItemCatMapper.insert(tbItemCat);
        }else{
            return tbItemCatMapper.updateByPrimaryKey(tbItemCat);
        }
    }

}
