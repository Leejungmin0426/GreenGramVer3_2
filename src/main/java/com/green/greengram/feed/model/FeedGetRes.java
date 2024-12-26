package com.green.greengram.feed.model;

import com.green.greengram.feed.comment.model.FeedCommentDto;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class FeedGetRes {
    private final boolean moreComment;
    @Schema(title = "피드 PK")
    private long feedId;
    @Schema(title = "피드 내용")
    private String contents;
    @Schema(title = "피드 위치")
    private String location;
    @Schema(title = "피드 생성일시")
    private String createdAt;
    @Schema(title = "작성자 유저 PK")
    private long writerUserId;
    @Schema(title = "작성자 유저 이름")
    private String writerNm;
    @Schema(title = "작성자 유저 프로필 사진파일명")
    private String writerPic;
    @Schema(title = "좋아요", description = "1: 좋아요, 0: 좋아요 아님")
    private int isLike;

    @Schema(title = "피드 사진 리스트")
    private List<String> pics;
    @Schema(title = "피드 댓글")
    private FeedCommentGetRes comment;// 댓글과 관련된 정보가 들어가 있다. 레퍼런스 변수라 주소값 들어가 있다. FeedCommentGetRes의 주소값.



    // 기본 생성자
    public FeedGetRes() {
        this.pics = new ArrayList<>();
        this.comment = new FeedCommentGetRes();
        this.moreComment = false;
    }

    public FeedGetRes(FeedWithPicCommentDto dto) {
        this.feedId = dto.getFeedId();
        this.contents = dto.getContents();
        this.location = dto.getLocation();
        this.createdAt = dto.getCreatedAt();
        this.writerUserId = dto.getWriterUserId();
        this.writerNm = dto.getWriterNm();
        this.writerPic = dto.getWriterPic();
        this.isLike = dto.getIsLike();
        this.pics = dto.getPics();
        // getCommentList()가 null일 경우 빈 리스트로 초기화
        List<FeedCommentDto> commentList = dto.getCommentList();
        if (commentList == null) {
            commentList = new ArrayList<>();
        }

        // 댓글 개수에 따라 moreComment 설정
        this.moreComment = commentList.size() > 4;

        // 댓글 리스트 처리
        FeedCommentGetRes commentRes = new FeedCommentGetRes();
        commentRes.setCommentList(commentList.size() > 4
                ? commentList.subList(0, 4)
                : commentList);
        this.comment = commentRes;


        }

    }
