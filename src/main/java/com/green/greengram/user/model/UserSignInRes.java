package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인 응답")
public class UserSignInRes {
    @JsonIgnore //swagger 표시 안 되지만, 응답 때 빼는 역할도 한다. 꼭 적길
    private String upw;

    private long userId;
    private String pic;
    private String nickName;

    @JsonIgnore //swagger 표시 안 되지만, 응답 때 빼는 역할도 한다. 꼭 적길
    private String message;
}
