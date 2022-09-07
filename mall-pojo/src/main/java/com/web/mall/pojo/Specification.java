package com.web.mall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/6 14:35
 */

/**
 * 结合类
 */
public class Specification implements Serializable {

    private TbSpecification tbSpecification;

    private List<TbSpecificationOption> tbSpecificationOptions;

    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public TbSpecification getTbSpecification() {
        return tbSpecification;
    }

    public void setTbSpecification(TbSpecification tbSpecification) {
        this.tbSpecification = tbSpecification;
    }

    public List<TbSpecificationOption> getTbSpecificationOptions() {
        return tbSpecificationOptions;
    }

    public void setTbSpecificationOptions(List<TbSpecificationOption> tbSpecificationOptions) {
        this.tbSpecificationOptions = tbSpecificationOptions;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "tbSpecification=" + tbSpecification +
                ", tbSpecificationOptions=" + tbSpecificationOptions +
                ", ids=" + ids +
                '}';
    }
}
