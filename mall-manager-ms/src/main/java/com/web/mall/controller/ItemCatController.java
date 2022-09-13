package com.web.mall.controller;

import com.web.mall.pojo.ResultBean;
import com.web.mall.pojo.TbItemCat;
import com.web.mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/9 10:07
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @GetMapping("/queryItemsByParentId/{id}")
    public ResultBean queryItemsByParentId(@PathVariable Long id){
        return ResultBean.ok(itemCatService.queryItemsByParentId(id));
    }

    @PostMapping("/updateOrInsert")
    public ResultBean updateOrInsert(@RequestBody TbItemCat tbItemCat){
        return ResultBean.ok(itemCatService.updateOrInsert(tbItemCat));
    }

}
