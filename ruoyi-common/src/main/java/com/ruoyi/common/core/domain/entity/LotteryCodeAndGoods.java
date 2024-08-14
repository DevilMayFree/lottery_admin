package com.ruoyi.common.core.domain.entity;

import java.io.Serializable;

public class LotteryCodeAndGoods implements Serializable {

    private String code;

    private LotteryGoods goods;

    public LotteryCodeAndGoods() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LotteryGoods getGoods() {
        return goods;
    }

    public void setGoods(LotteryGoods goods) {
        this.goods = goods;
    }
}
