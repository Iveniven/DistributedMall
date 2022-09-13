package com.web.mall.pojo;

import java.io.Serializable;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/8 19:50
 */
public class ReObject2 implements Serializable {

    private Long id;

    private String specName;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
