package com.green.greengram.feed.like;

import com.green.greengram.common.ResultResponse;
import com.green.greengram.common.model.MyFileUtils;
import com.green.greengram.feed.like.model.FeedLikeReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("feed/like")
@RequiredArgsConstructor
public class FeedLikeController {
    private final FeedLikeService service;
    private final MyFileUtils myFileUtils;


    @GetMapping
    @Operation(summary = "좋아요 처리", description = "특정 피드에 좋아요를 추가합니다.")
    public ResultResponse<Integer> feedLikeToggle (@ParameterObject @ModelAttribute FeedLikeReq p) {
        log.info("FeedLikeController > feedLikeToggle > p : {}", p);
        int result = service.feedLikeToggle(p);
        return ResultResponse.<Integer>builder()
                .resultMessage(result == 0 ? "좋아요 취소" : "좋아요 등록")
                .resultData(result)
                .build();
    }

}
