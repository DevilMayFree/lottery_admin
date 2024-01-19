package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class LotterySetReq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String rate;

    public LotterySetReq() {

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "LotterySetReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
