package com.green.greengram.feed.like;

import com.green.greengram.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedLikeService {
    private final FeedLikeMapper mapper;
    public int feedLikeToggle(FeedLikeReq p) {
        int result = mapper.delFeedLike(p);
        if (result == 0){
            return mapper.insFeedLike(p);
        }
        return 0;
    }

}
