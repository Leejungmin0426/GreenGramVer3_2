package com.green.greengram.user;

import com.green.greengram.common.ResultResponse;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> selUserByUid (@ParameterObject @ModelAttribute UserInfoGetReq p){
        log.info("userController > getUserInfo > p: {}", p);
        UserInfoGetRes res = service.getUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder()
                .resultMessage("유저 프로필 정보")
                .resultData(res)
                .build();
    }

    @PatchMapping("pic")
    public ResultResponse<String>  patchProfilePic(@ModelAttribute UserPicPatchReq p){
        log.info("UserControlloer > patchProfilePic > p : {}", p);
        String pic = service.patchUserPic(p);
        return ResultResponse.<String>builder()
                .resultMessage("프로필 사진 수정 완료!")
                .resultData(pic)
                .build();
    }





}
