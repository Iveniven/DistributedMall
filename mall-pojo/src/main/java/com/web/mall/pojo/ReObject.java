package com.web.mall.pojo;

import java.io.Serializable;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/8 14:49
 */
public class ReObject implements Serializable {

    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
