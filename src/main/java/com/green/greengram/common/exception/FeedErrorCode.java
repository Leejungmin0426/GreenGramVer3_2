package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor

public enum FeedErrorCode implements ErrorCode{
    FAIL_TO_REG(HttpStatus.INTERNAL_SERVER_ERROR, "사진은 필수입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
