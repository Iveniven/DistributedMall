package com.web.mall.service;

import com.web.mall.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {

    public List<TbItemCat> queryItemsByParentId(Long id);

    public int updateOrInsert(TbItemCat tbItemCat);
}
