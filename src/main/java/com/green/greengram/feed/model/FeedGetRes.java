package com.green.greengram.feed.model;

import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String createdAt;
    private String location;
    private long writerUserId;
    private String writerNm;
    private String writerPic;


    private List<String> pics;
    private FeedCommentGetRes comment;// 댓글과 관련된 정보가 들어가 있다. 레퍼런스 변수라 주소값 들어가 있다. FeedCommentGetRes의 주소값.
}
