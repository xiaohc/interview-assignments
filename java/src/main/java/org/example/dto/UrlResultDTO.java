/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validate.group.ShortUrl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 短域名服务的请求结果
 *
 * @author xiaohongchao
 * @since 1.0
 */
@ApiModel(description = "短域名服务的请求结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlResultDTO implements Serializable {
    private static final long serialVersionUID = 6703643383176755704L;

    /**
     * 成功响应码
     */
    private static final int SUCCESS_CODE = 0;

    /**
     * 成功响应消息
     */
    private static final String SUCCESS_MESSAGE = "成功";

    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码", name = "code", required = true, example = "" + SUCCESS_CODE)
    @NotNull(message = "响应码不能为空")
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息", name = "msg", required = true, example = SUCCESS_MESSAGE)
    @NotBlank(message = "响应消息不能为空")
    private String msg;

    /**
     * 请求内容: 域名内容
     */
    @ApiModelProperty(value = "域名内容", required = true, example = "https://github.com/xiaohc")
    @NotBlank(message = "域名内容不能为空")
    @Size(max = 8, message = "短域名长度最大为 8 个字符", groups = ShortUrl.class)
    private String domainName;

    /**
     * new 一个成功应答对象
     *
     * @param domainName 域名
     * @return 成功应答对象
     */
    public static UrlResultDTO successWithData(String domainName) {
        return new UrlResultDTO(SUCCESS_CODE, SUCCESS_MESSAGE, domainName);
    }

    /**
     * new 一个失败应答对象
     *
     * @param code 响应码
     * @param msg  响应消息
     * @return 失败应答对象
     */
    public static UrlResultDTO failWithCodeAndMsg(int code, String msg) {
        return new UrlResultDTO(code, msg, null);
    }
}
