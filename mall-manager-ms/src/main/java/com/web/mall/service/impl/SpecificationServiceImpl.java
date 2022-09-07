package com.web.mall.service.impl;

import com.web.mall.mapper.TbSpecificationMapper;
import com.web.mall.mapper.TbSpecificationOptionMapper;
import com.web.mall.pojo.*;
import com.web.mall.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/6 14:39
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;

    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    //查询规格列表
    @Override
    public List<TbSpecification> queryAllSpec() {
        List<TbSpecification> specifications = tbSpecificationMapper.selectByExample(null);
        return specifications;
    }

    //查询规格组合对象
    @Override
    public Specification querySpec(TbSpecification tbSpecification) {
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);

        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specification.setTbSpecificationOptions(tbSpecificationOptionMapper.selectByExample(example));
        return specification;
    }

    //删除
    @Override
    public int deleteSpecById(Long id) {
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        tbSpecificationOptionMapper.deleteByExample(example);
        return tbSpecificationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateOrInsert(Specification specification) {
        //新建
        if (specification.getTbSpecification().getId() == null || specification.getTbSpecification().getId() == 0) {
            tbSpecificationMapper.insert(specification.getTbSpecification());
            for (TbSpecificationOption t : specification.getTbSpecificationOptions()) {
                t.setSpecId(specification.getTbSpecification().getId());
                tbSpecificationOptionMapper.insert(t);
            }
        }
        //更新
        else {
            tbSpecificationMapper.updateByPrimaryKey(specification.getTbSpecification());
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(specification.getIds());
            if(specification.getIds().size()>0){
                tbSpecificationOptionMapper.deleteByExample(example);
            }
            for (TbSpecificationOption t : specification.getTbSpecificationOptions()) {
                if (t.getId() == null || t.getId().equals(0)) {
                    t.setSpecId(specification.getTbSpecification().getId());
                    tbSpecificationOptionMapper.insert(t);
                } else {
                    tbSpecificationOptionMapper.updateByPrimaryKey(t);
                }
            }
        }

        return 0;
    }
}
