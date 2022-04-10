package com.develop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author miao
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lc_improve_proposal")
@ApiModel(value = "LcImproveProposal对象", description = "")
public class LcImproveProposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "proposal_id", type = IdType.AUTO)
    private Long proposalId;

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contact_person")
    private String contactPerson;

    @ApiModelProperty(value = "联系方式")
    @TableField("contact_phone")
    private String contactPhone;

    @ApiModelProperty(value = "公司名称")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "产品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "产品编号")
    @TableField("product_number")
    private String productNumber;

    @ApiModelProperty(value = "建议日期")
    @TableField("proposal_time")
    private Timestamp proposalTime;

    @ApiModelProperty(value = "改进建议")
    @TableField("proposal_info_id")
    private String proposalInfoId;

    @TableField(exist = false)
    private List<String> proposalInfo;

    @ApiModelProperty(value = "图片上传id")
    @TableField("image_id")
    private String imageId;

    @ApiModelProperty(value = "视频上传id")
    @TableField("vedio_id")
    private String vedioId;

    @ApiModelProperty(value = "删除标志")
    @TableField("delflag")
    private Boolean delflag;

    @ApiModelProperty(value = "用户微信openid")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "采纳建议的回复内容")
    @TableField("response")
    private String response;

    @ApiModelProperty(value = "受理标志")
    @TableField("accept_flag")
    private Boolean acceptFlag;

    @ApiModelProperty(value = "受理人")
    @TableField("accept_person")
    private String acceptPerson;

    @ApiModelProperty(value = "受理时间")
    @TableField("accept_time")
    private Timestamp acceptTime;


    @TableField(exist = false)
    private List<String> videoList;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private Integer size;

    @TableField(exist = false)
    private String queryStr;
}
