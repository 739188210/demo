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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author miao
 * @since 2022-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lc_satisfaction_survey")
@ApiModel(value = "LcSatisfactionSurvey对象", description = "")
public class LcSatisfactionSurvey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "survey_id", type = IdType.AUTO)
    private Long surveyId;

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

    @ApiModelProperty(value = "服务单号")
    @TableField("service_order_number")
    private String serviceOrderNumber;

    @ApiModelProperty(value = "服务工单照片地址")
    @TableField("service_order_url")
    private String serviceOrderUrl;

    @ApiModelProperty(value = "服务工程师")
    @TableField("service_engineer")
    private String serviceEngineer;

    @ApiModelProperty(value = "服务需求受理答复速度")
    @TableField("response_speed")
    private BigDecimal responseSpeed;

    @ApiModelProperty(value = "服务需求现场处理响应速度")
    @TableField("process_speed")
    private BigDecimal processSpeed;

    @ApiModelProperty(value = "现场问题处理准备度")
    @TableField("prepare_ability")
    private BigDecimal prepareAbility;

    @ApiModelProperty(value = "现场处理问题专业能力")
    @TableField("process_ability")
    private BigDecimal processAbility;

    @ApiModelProperty(value = "整体职业化形象服务态度")
    @TableField("figure_attitude")
    private BigDecimal figureAttitude;

    @ApiModelProperty(value = "现场沟通交流职业化服务态度")
    @TableField("communication_attitude")
    private BigDecimal communicationAttitude;

    @ApiModelProperty(value = "综合评价")
    @TableField("complex_evaluate")
    private BigDecimal complexEvaluate;

    @ApiModelProperty(value = "服务评价内容")
    @TableField("service_evaluation_id")
    private String serviceEvaluationId;

    @TableField(exist = false)
    private List<String> serviceEvaluation;

    @ApiModelProperty(value = "设备评价内容")
    @TableField("device_evaluation_id")
    private String deviceEvaluationId;

    @TableField(exist = false)
    private List<String> deviceEvaluation;


    @ApiModelProperty(value = "图片上传id")
    @TableField("image_id")
    private String imageId;

    @ApiModelProperty(value = "视频上传id")
    @TableField("video_id")
    private String videoId;

    @ApiModelProperty(value = "满意度调研日期")
    @TableField("survey_time")
    private Timestamp surveyTime;

    @ApiModelProperty(value = "删除标志")
    @TableField("delflag")
    private Boolean delflag;

    @ApiModelProperty(value = "用户微信openid")
    @TableField("open_id")
    private String openId;

    @TableField(exist = false)
    private List<String> videoList;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private Integer size;

    @TableField(exist = false)
    private String queryStr;
}
