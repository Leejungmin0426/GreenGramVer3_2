package com.green.greengram.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getMessage(); //나를 상속받은 ENUM은 String message 멤버필드를 꼭 가져야 한다! 란 의미로 넣음 ~ impliments한 메소드에 꼭 메세지 있으라는 의미
    String name();
    HttpStatus getHttpStatus();// 응답 코드 결정
}
