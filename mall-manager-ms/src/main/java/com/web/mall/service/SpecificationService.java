package com.web.mall.service;

import com.web.mall.pojo.Specification;
import com.web.mall.pojo.TbSpecification;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/6 14:39
 */
public interface SpecificationService {

    public List<TbSpecification> queryAllSpec();

    public Specification querySpec(TbSpecification tbSpecification);

    public int deleteSpecById(Long id);

    public int updateOrInsert(Specification specification);

    public TbSpecification selectById(Long id);

    public List<TbSpecification> selectOtherBrands(List<Long> ids);

}
