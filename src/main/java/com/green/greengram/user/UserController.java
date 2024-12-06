package com.green.greengram.user;

import com.green.greengram.common.ResultResponse;
import com.green.greengram.user.model.UserSignInReq;
import com.green.greengram.user.model.UserSignInRes;
import com.green.greengram.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입")
    public ResultResponse<Integer> insUser(@RequestPart UserSignUpReq p, @RequestPart(required = false) MultipartFile pic){
       int result = service.insUser(p, pic);
        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입 완료입니다!")
                .resultData(result)
                .build();
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> selUserByUid (@RequestBody UserSignInReq p){
        UserSignInRes result = service.selUserByUid(p);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(result.getMessage())
                .resultData(result)
                .build();
    }
}
