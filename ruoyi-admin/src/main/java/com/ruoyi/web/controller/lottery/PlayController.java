package com.ruoyi.web.controller.lottery;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.LotteryCode;
import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.LotteryCodeMapper;
import com.ruoyi.system.mapper.LotteryGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 模板管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lottery/play")
public class PlayController extends BaseController {

    @Autowired
    private LotteryCodeMapper lotteryCodeMapper;

    @Autowired
    private LotteryGoodsMapper lotteryGoodsMapper;

    // 根据邀请码 查询前端页面需要的数据
    @PostMapping("/codeDetail")
    public AjaxResult codeDetail(@RequestBody LotteryCode code) {

        if (StringUtils.isBlank(code.getCode())) {
            throw new IllegalArgumentException("请输入邀请码");
        }
        LotteryCode lotteryCode = lotteryCodeMapper.findCodeByName(code.getCode());

        if (lotteryCode == null) {
            throw new IllegalArgumentException("非法邀请码");
        }
        // 可抽奖次数
        int count = lotteryCodeMapper.countLottery(code.getCode());

        if (count <= 0) {
            throw new IllegalArgumentException("抽奖次数为0,无法继续抽奖");
        }

        List<LotteryGoods> goodsInfoList = lotteryGoodsMapper.findGoodsInfoByCode(code.getCode());

        return AjaxResult.success(goodsInfoList);
    }

    // 根据邀请码进行一次抽奖
    @PostMapping("/lottery")
    public AjaxResult lottery(@RequestBody LotteryCode code) {

        if (StringUtils.isBlank(code.getCode())) {
            throw new IllegalArgumentException("请输入邀请码");
        }
        LotteryCode lotteryCode = lotteryCodeMapper.findCodeByName(code.getCode());

        if (lotteryCode == null) {
            throw new IllegalArgumentException("非法邀请码");
        }

        AjaxResult ajax = AjaxResult.success();
        synchronized (this) {
            // 可抽奖次数
            int count = lotteryCodeMapper.countLottery(code.getCode());

            if (count <= 0) {
                throw new IllegalArgumentException("抽奖次数为0,无法继续抽奖");
            }

            // 抽奖次数-1
            lotteryCodeMapper.updateCountLottery(code.getCode());

            List<LotteryGoods> goodsInfoRateList = lotteryGoodsMapper.findGoodsInfoAndRateByCode(code.getCode());

            List<LotteryGoods> rateList = goodsInfoRateList.stream()
                    .filter(i -> Float.parseFloat(i.getRate()) != 0F).collect(Collectors.toList());

            LotteryGoods draw = draw(rateList);
            if (draw != null) {
                int i = goodsInfoRateList.indexOf(draw);
                 ajax.put("index", i);
//                ajax.put("index", 0);
                ajax.put("goods", draw);
            }
        }
        return ajax;
    }

    private LotteryGoods draw(List<LotteryGoods> rateList) {
        Random random = new Random();
        int num = random.nextInt(10000); // 生成一个0到10000之间的随机数 8800= 88%

        long sum = 0;

        for (int i = 0; i < rateList.size(); i++) {
            sum += Integer.parseInt(rateList.get(i).getRate());

            if (num < sum) {
                return rateList.get(i);
            }
        }
        return null;
    }

}
