package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

public interface LotteryGoodsMapper {
    List<LotteryGoods> selectGoodsList(LotteryGoods goods);

    int insertGoods(LotteryGoods goods);

    LotteryGoods selectGoodsById(Long id);

    List<LotteryGoods> selectGoodsByName(String name);

    LotteryGoods findGoodsByName(String name);

    int updateGoods(LotteryGoods goods);

    int deleteGoodsByIds(Long[] ids);
}
