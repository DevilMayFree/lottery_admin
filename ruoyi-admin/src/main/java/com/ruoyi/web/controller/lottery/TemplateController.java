package com.ruoyi.web.controller.lottery;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.core.domain.entity.LotterySet;
import com.ruoyi.common.core.domain.entity.LotterySetReq;
import com.ruoyi.common.core.domain.entity.LotteryTemplate;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.LotteryGoodsMapper;
import com.ruoyi.system.mapper.LotterySetMapper;
import com.ruoyi.system.mapper.LotteryTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lottery/template")
public class TemplateController extends BaseController {

    @Autowired
    private LotteryTemplateMapper lotteryTemplateMapper;

    @Autowired
    private LotteryGoodsMapper lotteryGoodsMapper;

    @Autowired
    private LotterySetMapper lotterySetMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(LotteryTemplate template) {
        startPage();
        List<LotteryTemplate> list = lotteryTemplateMapper.selectTemplateList(template);

        /*for (LotteryTemplate lotteryTemplate : list) {
            List<LotterySetReq> goods = new ArrayList<>();
            List<LotterySet> lotterySetList = lotterySetMapper.selectSetListByTemplateId(lotteryTemplate.getId());

            for (LotterySet lotterySet : lotterySetList) {
                String rate = lotterySet.getRate();
                LotterySetReq req = new LotterySetReq();
                req.setName(lotterySet.getGoodsName());
                req.setRate(String.valueOf(Float.parseFloat(rate) / 100));
                goods.add(req);
            }

            lotteryTemplate.setGoods(goods);
        }*/
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody LotteryTemplate template) {
        System.out.println("add template:" + template);
        lotteryTemplateMapper.insertTemplate(template);
        Long templateId = template.getId();
        System.out.println("add template id :" + templateId);
        if (templateId == null) {
            return new AjaxResult(HttpStatus.ERROR, "添加中奖模板失败", null);
        }

        List<LotterySetReq> goods = template.getGoods();
        for (LotterySetReq good : goods) {
            String name = good.getName();
            String rate = String.valueOf(good.getRate());
            LotteryGoods lotteryGoods = lotteryGoodsMapper.findGoodsByName(name);

            if (lotteryGoods == null) {
                return new AjaxResult(HttpStatus.ERROR, "查无此奖品", name);
            }
            Long goodsId = lotteryGoods.getId();

            LotterySet set = new LotterySet();
            set.setTemplateId(templateId);
            set.setGoodsId(goodsId);
            set.setRate(rate);
            lotterySetMapper.insertSet(set);
        }
        return toAjax(true);
    }

    /**
     * 根据编号获取详细信息
     */
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(id)) {
            LotteryTemplate lotteryTemplate = lotteryTemplateMapper.selectTemplateById(id);

            List<LotterySetReq> goods = new ArrayList<>();
            List<LotterySet> lotterySetList = lotterySetMapper.selectSetListByTemplateId(lotteryTemplate.getId());

            for (LotterySet lotterySet : lotterySetList) {
                String rate = lotterySet.getRate();
                LotterySetReq req = new LotterySetReq();
                req.setName(lotterySet.getGoodsName());
                req.setRate(String.valueOf(Float.parseFloat(rate) / 100));
                goods.add(req);
            }

            lotteryTemplate.setGoods(goods);

            ajax.put(AjaxResult.DATA_TAG, lotteryTemplate);
        }
        return ajax;
    }

    /**
     * 根据name获取详细信息
     */
    @PostMapping("/getTemplatesByName")
    public AjaxResult getInfoByName(@RequestBody LotteryTemplate template) {
        AjaxResult ajax = AjaxResult.success();
        List<LotteryTemplate> list = lotteryTemplateMapper.selectTemplatesByName(template.getName());
        ajax.put(AjaxResult.DATA_TAG, list);
        return ajax;
    }

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LotteryTemplate template) {
        this.remove(new Long[]{template.getId()});
        template.setId(null);
        this.add(template);
        return AjaxResult.success();
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long[] id) {
        lotteryTemplateMapper.deleteTemplateByIds(id);
        lotterySetMapper.deleteSetByIds(id);
        return AjaxResult.success();
    }

}
