package com.green.greengram.feed.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
public class FeedCommentGetRes { //페이징의 기본생성자가 없어서
 private boolean moreComment; // 댓글이 더 있는지에 대한 정보

 private List<FeedCommentDto> commentList; //객체 안에 객체 있고, 객체안에 객체있다~ 구조적 관계

}
