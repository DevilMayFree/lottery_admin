package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.core.domain.entity.LotteryTemplate;

import java.util.List;

public interface LotteryTemplateMapper {
    List<LotteryTemplate> selectTemplateList(LotteryTemplate template);

    int insertTemplate(LotteryTemplate template);

    LotteryTemplate selectTemplateById(Long id);

    List<LotteryTemplate> selectTemplatesByName(String name);

    LotteryTemplate findTemplatesByName(String name);

    int updateTemplate(LotteryTemplate template);

    int deleteTemplateByIds(Long[] ids);
}
