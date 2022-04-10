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
@TableName("lc_user_info")
@ApiModel(value = "LcUserInfo对象", description = "")
public class LcUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "listenCenter用户表主键")
    @TableId(value = "lc_user_id", type = IdType.AUTO)
    private Long lcUserId;

    @ApiModelProperty(value = "用户微信openid")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "公司名称")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "联系方式")
    @TableField("contact_phone")
    private String contactPhone;

    @ApiModelProperty(value = "删除标志")
    @TableField("delflag")
    private Boolean delflag;


    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private Integer size;

    @TableField(exist = false)
    private String queryStr;


    public boolean equalsUserInfo(LcUserInfo lcUserInfo) {
        boolean bool = false;
        if (!this.getDelflag()
                && this.getOpenId().equals(lcUserInfo.getOpenId())
                && this.getUsername().equals(lcUserInfo.getUsername())
                && this.getCompanyName().equals(lcUserInfo.getCompanyName())
                && this.getContactPhone().equals(lcUserInfo.getContactPhone())) {
            bool = true;
        }
        return bool;
    }
}
