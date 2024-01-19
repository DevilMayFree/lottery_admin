package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class LotteryTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long count;

    private List<LotterySetReq> goods;

    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


    public LotteryTemplate() {

    }

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<LotterySetReq> getGoods() {
        return goods;
    }

    public void setGoods(List<LotterySetReq> goods) {
        this.goods = goods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "LotteryTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", goods=" + goods +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
