package com.web.mall.service.impl;

import com.web.mall.mapper.TbBrandMapper;
import com.web.mall.pojo.TbBrand;
import com.web.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/5 16:59
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    //查询brand列表
    @Override
    public List<TbBrand> queryBrands() {
        return tbBrandMapper.selectByExample(null);
    }

    //修改或者添加
    @Override
    public int updateOrInsert(TbBrand tbBrand) {
        if(tbBrand.getId()==null || tbBrand.getId()==0){
            return tbBrandMapper.insert(tbBrand);
        }else {
            return tbBrandMapper.updateByPrimaryKey(tbBrand);
        }
    }

    //删除
    @Override
    public int deleteBrand(Long id) {
        return tbBrandMapper.deleteByPrimaryKey(id);
    }
}
