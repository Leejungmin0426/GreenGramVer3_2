package com.green.greengram.feed;

import com.green.greengram.feed.model.FeedDelReq;
import com.green.greengram.feed.model.FeedGetReq;
import com.green.greengram.feed.model.FeedPicDto;
import com.green.greengram.feed.model.FeedPicSel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicMapper {
    int insFeedPic(FeedPicDto p);
    int insFeedPic2 (FeedPicDto p);
    List<String> selFeedPic (long feedId);
    List<FeedPicSel> selFeedPicListByFeedIds (List<Long> feedIds);

}
