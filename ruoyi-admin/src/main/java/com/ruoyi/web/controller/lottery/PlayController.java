package com.ruoyi.web.controller.lottery;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.LotteryCode;
import com.ruoyi.common.core.domain.entity.LotteryCodeAndGoods;
import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.LotteryCodeMapper;
import com.ruoyi.system.mapper.LotteryGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    private static final String REDIS_KEY = "lottery:log:%s";

    @Autowired
    private LotteryCodeMapper lotteryCodeMapper;

    @Autowired
    private LotteryGoodsMapper lotteryGoodsMapper;

    @Autowired
    private RedisCache redisCache;

    // 根据邀请码 查询前端页面需要的数据
    @PostMapping("/codeDetail")
    public AjaxResult codeDetail(@RequestBody LotteryCode code) {

        if (StringUtils.isBlank(code.getCode())) {
            return AjaxResult.warn("請輸入邀請碼");
        }
        LotteryCode lotteryCode = lotteryCodeMapper.findCodeByName(code.getCode());

        if (lotteryCode == null) {
            return AjaxResult.warn("非法邀请码");
        }
        // 可抽奖次数
        Integer count = lotteryCodeMapper.countLottery(code.getCode());

        if (count == null || count <= 0) {
            return AjaxResult.warn("抽獎次數為0,無法繼續抽獎");
        }

        List<LotteryGoods> goodsInfoList = lotteryGoodsMapper.findGoodsInfoByCode(code.getCode());

        // 使用了未中奖
        lotteryCodeMapper.actionCodeDone(lotteryCode.getId(), 1);

        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, goodsInfoList);
        ajax.put("lotteryCount", count);

        return ajax;
    }

    // 根据邀请码进行一次抽奖
    @PostMapping("/lottery")
    public AjaxResult lottery(@RequestBody LotteryCode code) {

        if (StringUtils.isBlank(code.getCode())) {
            return AjaxResult.warn("請輸入邀請碼");
        }
        LotteryCode lotteryCode = lotteryCodeMapper.findCodeByName(code.getCode());

        if (lotteryCode == null) {
            return AjaxResult.warn("非法邀請碼");
        }

        AjaxResult ajax = AjaxResult.success();
        synchronized (this) {
            // 可抽奖次数
            Integer count = lotteryCodeMapper.countLottery(code.getCode());

            if (count == null || count <= 0) {
                return AjaxResult.warn("抽獎次數為0,無法繼續抽獎");
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

    // 根据邀请码进行一次抽奖
    @PostMapping("/returnLottery")
    public AjaxResult returnLottery(@RequestBody LotteryCodeAndGoods req) {

        LotteryGoods goods = req.getGoods();
        String code = req.getCode();
        String redisKey = String.format(REDIS_KEY, code);
        goods.setLogTime(new Date());
        Long redisReturn = redisCache.lPush(redisKey, goods, 3600 * 24 * 7);

        if (redisReturn == null) {
            return AjaxResult.warn("抽獎記錄失敗");
        }
        LotteryCode lotteryCode = new LotteryCode();
        lotteryCode.setCode(code);
        return lottery(lotteryCode);
    }


    @PostMapping("/query")
    public AjaxResult query(@RequestBody LotteryCode code) {

        if (StringUtils.isBlank(code.getCode())) {
            return AjaxResult.warn("請輸入邀請碼");
        }
        LotteryCode lotteryCode = lotteryCodeMapper.findCodeByName(code.getCode());

        if (lotteryCode == null) {
            return AjaxResult.warn("非法邀請碼");
        }

        String redisKey = String.format(REDIS_KEY, code.getCode());
        List<Object> objects = redisCache.rangeAll(redisKey);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("list", objects);
        return ajax;
    }

}
