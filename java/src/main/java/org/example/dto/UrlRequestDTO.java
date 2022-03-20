/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validate.group.ShortUrl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短域名服务的请求信息
 *
 * @author xiaohongchao
 * @since 1.0
 */
@ApiModel(description = "短域名服务的请求信息")
@Data
@NoArgsConstructor
public class UrlRequestDTO implements Serializable {
    private static final long serialVersionUID = 3687720469693845694L;

    /**
     * 请求IP
     */
    @ApiModelProperty(value = "请求IP", required = true, example = "192.168.0.112")
    @NotBlank(message = "请求IP不能为空")
    @Pattern(regexp = "(?:(?:1[0-9][0-9]\\.)|(?:2[0-4][0-9]\\.)|(?:25[0-5]\\.)|(?:[1-9][0-9]\\.)|(?:[0-9]\\.)){3}"
            + "(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))",
            message = "请求IP必须是有效的IPv4地址")
    private String ip;

    /**
     * 请求时间
     */
    @ApiModelProperty(value = "请求时间", required = true, example = "2022-01-15T09:10:47.507Z")
    @NotNull(message = "请求时间不能为空")
    private LocalDateTime requestTime;

    /**
     * 请求内容: 域名内容
     * 短域名请求内容为24位： 服务地址16位 + 短域名特征码8位
     * e.g.  https://t.hk.uy/AADAqArU 中的 AADAqArU
     */
    @ApiModelProperty(value = "域名内容", required = true, example = "https://github.com/xiaohc")
    @NotBlank(message = "域名内容不能为空")
    @Size(max = 24, message = "请输入正确的短域名", groups = ShortUrl.class)
    private String domainName;
}
