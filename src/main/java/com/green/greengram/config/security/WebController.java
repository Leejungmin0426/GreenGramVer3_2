package com.green.greengram.config.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/{path:[^\\.]*}") // 모든 비정적 경로 처리
    public String redirect() {
        return "forward:/index.html"; // 정적 리소스의 index.html 반환
    }
}