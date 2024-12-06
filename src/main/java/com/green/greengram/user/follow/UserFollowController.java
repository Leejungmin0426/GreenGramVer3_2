package com.green.greengram.user.follow;

import com.green.greengram.common.ResultResponse;
import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user/follow")
@RequiredArgsConstructor
public class UserFollowController {
    private final UserFollowService service;

    // 팔로우 신청: RequestBody를 쓴다는 것은 요청을 보내는 자가 body에 json 형태의 데이터를 담아 보낸다는 것,,,,,
    @PostMapping

    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        log.info("UserFolowController > postUserFollow > p:{}", p);
        int result = service.postUserFollow(p);
        String message = (result == 0) ? "이미 팔로우 상태입니다." : "팔로우 신청 성공!";
        return ResultResponse.<Integer>builder()
                .resultMessage(message)
                .resultData(result)
                .build();
    }


    @DeleteMapping

    public ResultResponse<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        log.info("UserFolowController > deleteUserFollow > p:{}", p);
        int result = service.deleteUserFollow(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("팔로우 해제")
                .resultData(result)
                .build();
    }
}
