package com.ruoyi.web.controller.lottery;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.LotteryCode;
import com.ruoyi.common.core.domain.entity.LotteryTemplate;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.mapper.LotteryCodeMapper;
import com.ruoyi.system.mapper.LotteryTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lottery/code")
public class CodeController extends BaseController {

    @Value("${share.path}")
    private String share;

    @Autowired
    private LotteryCodeMapper lotteryCodeMapper;

    @Autowired
    private LotteryTemplateMapper lotteryTemplateMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(LotteryCode code) {
        startPage();
        List<LotteryCode> list = lotteryCodeMapper.selectCodeList(code);

        return getDataTable(list);
    }

    @PostMapping("/getCode")
    public AjaxResult getCode() {
        AjaxResult ajax = AjaxResult.success();
        String replace = UUID.randomUUID().toString().replace("-", "");
        ajax.put(AjaxResult.DATA_TAG, replace);
        return ajax;
    }

    // 兑奖
    @PostMapping("/action")
    public AjaxResult action(@RequestBody LotteryCode code) {
        AjaxResult ajax = AjaxResult.success();
        Long id = code.getId();
        // 3 对兑换
        int i = lotteryCodeMapper.actionCodeDone(id, 3);
        ajax.put(AjaxResult.DATA_TAG, i);
        return ajax;
    }

    @PostMapping("/getShare")
    public AjaxResult getSharePath() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, this.getShare());
        return ajax;
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody LotteryCode code) {
        String templateName = code.getTemplateName();
        LotteryTemplate template = lotteryTemplateMapper.findTemplatesByName(templateName);
        code.setTemplateId(template.getId());

        System.out.println("add code:" + code);
        lotteryCodeMapper.insertCode(code);
        return toAjax(true);
    }

    /**
     * 根据编号获取详细信息
     */
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(id)) {
            LotteryCode lotteryCode = lotteryCodeMapper.selectCodeById(id);
            ajax.put(AjaxResult.DATA_TAG, lotteryCode);
        }
        return ajax;
    }

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LotteryCode code) {
        this.remove(new Long[]{code.getId()});
        code.setId(null);
        this.add(code);
        return AjaxResult.success();
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long[] id) {
        lotteryCodeMapper.deleteCodeByIds(id);
        return AjaxResult.success();
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
