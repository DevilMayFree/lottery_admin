package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.LotteryCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LotteryCodeMapper {
    List<LotteryCode> selectCodeList(LotteryCode code);

    LotteryCode findCodeByName(String name);

    Integer countLottery(String code);

    Integer updateCountLottery(String code);

    Integer insertCode(LotteryCode code);

    Integer actionCodeDone(@Param("id") Long id, @Param("status") Integer status);

    LotteryCode selectCodeById(Long id);

    Integer deleteCodeByIds(Long[] ids);
}
