package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.LotterySet;
import com.ruoyi.common.core.domain.entity.LotteryTemplate;

import java.util.List;

public interface LotterySetMapper {

    int insertSet(LotterySet set);

    List<LotterySet> selectSetListByTemplateId(Long templateId);

    int deleteSetByIds(Long[] templateIds);
}
