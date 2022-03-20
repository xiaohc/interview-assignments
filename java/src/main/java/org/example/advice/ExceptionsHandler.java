/*
 * Copyright (c) 2022 xiaohongchao.All Rights Reserved.
 */
package org.example.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UrlResultDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * 统一异常处理
 *
 * @author xiaohongchao
 * @since 1.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    /**
     * 请求参数校验不通过异常
     *
     * @param exception 异常信息
     * @return 响应实体
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UrlResultDTO> handlerException(MethodArgumentNotValidException exception) {
        log.warn("Found exception.", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(UrlResultDTO.failWithCodeAndMsg(
                        HttpStatus.BAD_REQUEST.value(),
                        buildErrorMessage(exception)));
    }

    /**
     * 构建出错提示信息
     * e.g. [短域名长度最大为 8 个字符, 请求IP不能为空]
     *
     * @param exception 异常信息
     * @return 输入信息的出错提示
     */
    private String buildErrorMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * 异常兜底处理
     *
     * @param exception 异常信息
     * @return 响应实体
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UrlResultDTO> handlerException(Exception exception) {
        log.error("Found unknown exception.", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UrlResultDTO.failWithCodeAndMsg(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        exception.getMessage()));
    }
}
