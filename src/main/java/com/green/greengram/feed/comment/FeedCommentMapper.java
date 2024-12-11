package com.green.greengram.feed.comment;



import com.green.greengram.feed.comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    void insFeedComment(FeedCommentPostReq p);
    List<FeedCommentDto> selFeedCommentList(FeedCommentGetReq p);
    int delFeedComment(FeedCommentDelReq p);
    List<FeedCommentDto> selFeedCommentListByFeedIdsLimit4(List<Long> feedIds);
}