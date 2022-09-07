package com.web.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.mall.pojo.*;
import com.web.mall.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/6 14:56
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    //查询规格列表
    @GetMapping("/queryAllSpec")
    public ResultBean queryAllSpec(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        Page<TbSpecification> page = (Page<TbSpecification>) specificationService.queryAllSpec();
        PageResp resp = new PageResp();
        resp.setList(page.getResult());
        resp.setTotal(page.getTotal());
        return ResultBean.ok(resp);
    }


    //查询规格组合对象
    @PostMapping("/querySpec")
    public ResultBean querySpec(@RequestBody TbSpecification tbSpecifications){
        return ResultBean.ok(specificationService.querySpec(tbSpecifications));
    }

    //删除
    @DeleteMapping("/deleteSpec/{id}")
    public ResultBean deleteSpec(@PathVariable Long id){
        specificationService.deleteSpecById(id);
        return ResultBean.ok();
    }

    //新增或者更新
    @PostMapping("/updateOrInsert")
    public ResultBean updateOrInsert(@RequestBody Specification specification){
        specificationService.updateOrInsert(specification);
        return ResultBean.ok();
    }

}
