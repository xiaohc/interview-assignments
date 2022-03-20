/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.validate.group;

import javax.validation.groups.Default;

/**
 * 短域名校验分组
 * 注意：Default的子类，故校验时默认会包含 Default分组
 *
 * @author xiaohongchao
 * @since 1.0
 */
public interface ShortUrl extends Default {
}
