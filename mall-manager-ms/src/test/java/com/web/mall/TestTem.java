package com.web.mall;

import com.google.gson.Gson;
import com.web.mall.pojo.ReObject;
import com.web.mall.pojo.TbBrand;
import com.web.mall.pojo.TbSpecification;
import com.web.mall.pojo.TbTypeTemplate;
import com.web.mall.service.BrandService;
import com.web.mall.service.SpecificationService;
import com.web.mall.service.TemplateService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/7 19:06
 */
@SpringBootTest
public class TestTem {
    //
//    @Autowired
//    private TemplateService templateService;
//
//    @Test
//    public void test(){
//        Gson gson = new Gson();
//        List<TbTypeTemplate> list =  templateService.queryAllTemp();
//        for (TbTypeTemplate t:list) {
//            List brands = gson.fromJson(t.getBrandIds(), List.class);
//            t.setTbBrands(brands);
//            List specs = gson.fromJson(t.getSpecIds(), List.class);
//            t.setTbSpecifications(specs);
//            List items = gson.fromJson(t.getSpecIds(), List.class);
//            t.setAttributeItems(items);
//            System.out.println(t);
//        }
//    }

    @Autowired
    private TemplateService templateService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpecificationService specificationService;


    @SneakyThrows
    @Test
    public void initTemp() {
        Gson gson = new Gson();
        List<TbTypeTemplate> list = templateService.queryAllTemp();
        for (TbTypeTemplate t : list) {
            List brands = gson.fromJson(t.getBrandIds(), List.class);
            List<TbBrand> tbBrands = new ArrayList<>();
            for (Object obj:brands) {
                ReObject object = gson.fromJson(obj.toString(),ReObject.class);
                System.out.println(object.getId());
                tbBrands.add(brandService.selectById(object.getId()));
            }
            t.setBrandIds(gson.toJson(tbBrands));

            List spec = gson.fromJson(t.getSpecIds(), List.class);
            List<TbSpecification> tbSpecifications = new ArrayList<>();
            for (Object obj:spec) {
                ReObject object = gson.fromJson(obj.toString(),ReObject.class);
                System.out.println(object.getId());
                tbSpecifications.add(specificationService.selectById(object.getId()));
            }
            t.setSpecIds(gson.toJson(tbSpecifications));
            templateService.updateOrInsert(t);
        }
    }
}
