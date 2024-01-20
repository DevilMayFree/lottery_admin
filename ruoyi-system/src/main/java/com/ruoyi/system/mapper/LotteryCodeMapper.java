package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.LotteryCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LotteryCodeMapper {
    List<LotteryCode> selectCodeList(LotteryCode code);

    LotteryCode findCodeByName(String name);

    int countLottery(String code);

    int updateCountLottery(String code);

    int insertCode(LotteryCode code);

    int actionCodeDone(@Param("id") Long id, @Param("status") Integer status);

    LotteryCode selectCodeById(Long id);

    int deleteCodeByIds(Long[] ids);
}
