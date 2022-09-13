package com.web.mall.pojo;

import java.io.Serializable;
import java.util.List;

public class TbTypeTemplate extends PageReq implements Serializable {
    private Long id;

    /**
     * 模板名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 关联规格
     *
     * @mbg.generated
     */
    private String specIds;

    /**
     * 关联品牌
     *
     * @mbg.generated
     */
    private String brandIds;

    /**
     * 自定义属性
     *
     * @mbg.generated
     */
    private String customAttributeItems;

//    private List<TbBrand> tbBrands;
//
//    private List<TbSpecification> tbSpecifications;
//
//    private List<String> attributeItems;

    private static final long serialVersionUID = 1L;

//    public List<String> getAttributeItems() {
//        return attributeItems;
//    }
//
//    public void setAttributeItems(List<String> attributeItems) {
//        this.attributeItems = attributeItems;
//    }
//
//    public List<TbBrand> getTbBrands() {
//        return tbBrands;
//    }
//
//    public void setTbBrands(List<TbBrand> tbBrands) {
//        this.tbBrands = tbBrands;
//    }
//
//    public List<TbSpecification> getTbSpecifications() {
//        return tbSpecifications;
//    }
//
//    public void setTbSpecifications(List<TbSpecification> tbSpecifications) {
//        this.tbSpecifications = tbSpecifications;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }

    @Override
    public String toString() {
        return "TbTypeTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specIds='" + specIds + '\'' +
                ", brandIds='" + brandIds + '\'' +
                ", customAttributeItems='" + customAttributeItems + '\'' +
                '}';
    }
}