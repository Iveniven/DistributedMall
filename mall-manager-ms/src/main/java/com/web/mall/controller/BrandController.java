package com.web.mall.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.mall.pojo.PageReq;
import com.web.mall.pojo.PageResp;
import com.web.mall.pojo.ResponseStatus;
import com.web.mall.pojo.ResultBean;
import com.web.mall.pojo.TbBrand;
import com.web.mall.service.BrandService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/5 16:55
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //查询品牌列表
    @GetMapping("/queryBrands")
    public ResultBean queryBrands(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        Page<TbBrand> page = (Page<TbBrand>) brandService.queryBrands();
        PageResp resp = new PageResp();
        resp.setList(page.getResult());
        resp.setTotal(page.getTotal());
        return ResultBean.ok(resp);
    }

    //更新或者新建品牌
    @PostMapping("/updateOrInsert")
    public ResultBean updateOrInsert(@RequestBody TbBrand tbBrand) {
        tbBrand.setFirstChar(pinyin4(tbBrand.getName()));
        int i = brandService.updateOrInsert(tbBrand);
        if (i > 0) {
            return ResultBean.ok();
        } else {
            return ResultBean.fail(ResponseStatus.ERROR);
        }
    }

    //删除品牌
    @DeleteMapping("/deleteBrand/{id}")
    public ResultBean deleteBrand(@PathVariable Long id) {
        int i = brandService.deleteBrand(id);
        if (i > 0) {
            return ResultBean.ok();
        } else {
            return ResultBean.fail(ResponseStatus.ERROR);
        }
    }


    //返回字符串首字的拼音首字母
    public String pinyin4(String str){
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        String firstChar = null;
        try {
            firstChar= String.valueOf(PinyinHelper.toHanyuPinyinStringArray(str.charAt(0), defaultFormat)[0].charAt(0));
            return firstChar;
        } catch (Exception e) {
            return String.valueOf(str.charAt(0)).toUpperCase();
        }
    }
}
