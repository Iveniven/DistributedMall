package com.web.mall.service;

import com.web.mall.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    public List<TbBrand> queryBrands();

    public int updateOrInsert(TbBrand tbBrand);

    public int deleteBrand(Long id);
}
