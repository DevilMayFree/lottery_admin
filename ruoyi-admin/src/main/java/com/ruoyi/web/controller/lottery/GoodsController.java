package com.ruoyi.web.controller.lottery;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.LotteryGoods;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.LotteryGoodsMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 奖品管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lottery/goods")
public class GoodsController extends BaseController {

    @Value("${upload.path:.\\upload}")
    private String filePath;

    @Autowired
    private LotteryGoodsMapper lotteryGoodsMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(LotteryGoods goods) {
        startPage();
        List<LotteryGoods> list = lotteryGoodsMapper.selectGoodsList(goods);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody LotteryGoods goods) {
        return toAjax(lotteryGoodsMapper.insertGoods(goods));
    }

    /**
     * 根据编号获取详细信息
     */
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(id)) {
            LotteryGoods lotteryGoods = lotteryGoodsMapper.selectGoodsById(id);
            ajax.put(AjaxResult.DATA_TAG, lotteryGoods);
        }
        return ajax;
    }

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LotteryGoods goods) {
        return toAjax(lotteryGoodsMapper.updateGoods(goods));
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long[] id) {
        return toAjax(lotteryGoodsMapper.deleteGoodsByIds(id));
    }

    @PostMapping("/uploadGoods")
    public AjaxResult uploadGoods(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        // 创建保存目录
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 保存文件到指定目录
            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(new File(dir, fileName));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.success(fileName);
    }

}
