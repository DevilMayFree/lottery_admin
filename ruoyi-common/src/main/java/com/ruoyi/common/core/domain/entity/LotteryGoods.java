package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 * 
 * @author ruoyi
 */
public class LotteryGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long count;

    private String image;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    // 抽奖概率
    private String rate;

    public LotteryGoods()
    {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "LotteryGoods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
