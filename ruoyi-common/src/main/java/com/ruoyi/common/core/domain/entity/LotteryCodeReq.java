package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class LotteryCodeReq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    public LotteryCodeReq() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LotterySetReq{" +
                ", name='" + name + '\'' +
                '}';
    }
}
