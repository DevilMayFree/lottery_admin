package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;

public class LotteryCode extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private Long templateId;

    private String templateName;

    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


    public LotteryCode() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "LotteryCode{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", templateId=" + templateId +
                ", templateName='" + templateName + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
