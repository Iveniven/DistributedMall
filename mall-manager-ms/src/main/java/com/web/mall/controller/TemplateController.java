package com.web.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.web.mall.pojo.*;
import com.web.mall.service.BrandService;
import com.web.mall.service.SpecificationService;
import com.web.mall.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/7 17:23
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("/queryAllTemp")
    public ResultBean queryAllTemp(PageReq pageReq) {
        initTemp();
        PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) templateService.queryAllTemp();
        PageResp resp = new PageResp();
        resp.setList(page.getResult());
        resp.setTotal(page.getTotal());
        return ResultBean.ok(resp);
    }

    @GetMapping("/queryAllTempList")
    public ResultBean queryAllTempList() {
        initTemp();
        return ResultBean.ok(templateService.queryAllTemp());
    }

    @PostMapping("/queryNotSelectTbBrand")
    public ResultBean queryNotSelectTbBrand(@RequestBody List<TbBrand> tbBrands) {
        List<Long> ids = new ArrayList<>();
        for (TbBrand t :tbBrands) {
            ids.add(t.getId());
        }
        tbBrands = brandService.selectOtherBrands(ids);
        return ResultBean.ok(tbBrands);
    }

    @PostMapping("/queryNotSelectSpecs")
    public ResultBean queryNotSelectSpecs(@RequestBody List<TbSpecification> tbSpecifications) {
        List<Long> ids = new ArrayList<>();
        for (TbSpecification t :tbSpecifications) {
            ids.add(t.getId());
        }
        tbSpecifications = specificationService.selectOtherBrands(ids);
        return ResultBean.ok(tbSpecifications);
    }

    @PostMapping("/updateOrInsert")
    public ResultBean updateOrInsert(@RequestBody TbTypeTemplate tbTypeTemplate) {
        templateService.updateOrInsert(tbTypeTemplate);
        return ResultBean.ok();
    }

    @DeleteMapping("/deleteTemp/{id}")
    public ResultBean deleteTemp(@PathVariable Long id) {
        templateService.deleteTemp(id);
        return ResultBean.ok();
    }


    //初始化
    public void initTemp() {
        Gson gson = new Gson();
        List<TbTypeTemplate> list = templateService.queryAllTemp();
        for (TbTypeTemplate t : list) {
            List brands = gson.fromJson(t.getBrandIds(), List.class);
            List<TbBrand> tbBrands = new ArrayList<>();
            List<ReObject> objects = new ArrayList<>();
            for (Object obj:brands) {
                ReObject object = gson.fromJson(obj.toString(),ReObject.class);
                tbBrands.add(brandService.selectById(object.getId()));
            }
            for (TbBrand tt:tbBrands) {
                ReObject object = new ReObject();
                object.setId(tt.getId());
                object.setName(tt.getName());
                objects.add(object);
            }
            t.setBrandIds(gson.toJson(objects));

            List<ReObject2> objectList = new ArrayList<>();
            List spec = gson.fromJson(t.getSpecIds(), List.class);
            List<TbSpecification> tbSpecifications = new ArrayList<>();
            for (Object obj:spec) {
                ReObject object = gson.fromJson(obj.toString(),ReObject.class);
                tbSpecifications.add(specificationService.selectById(object.getId()));
            }
            for (TbSpecification tt:tbSpecifications) {
                ReObject2 object = new ReObject2();
                object.setId(tt.getId());
                object.setSpecName(tt.getSpecName());
                objectList.add(object);
            }
            t.setSpecIds(gson.toJson(objectList));
            templateService.updateOrInsert(t);
        }
    }

}
