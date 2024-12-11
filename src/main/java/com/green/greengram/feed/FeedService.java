package com.green.greengram.feed;

import com.green.greengram.common.model.MyFileUtils;
import com.green.greengram.feed.comment.FeedCommentMapper;
import com.green.greengram.feed.comment.model.*;
import com.green.greengram.feed.like.FeedLikeMapper;
import com.green.greengram.feed.like.FeedLikeService;
import com.green.greengram.feed.like.model.FeedLikeReq;
import com.green.greengram.feed.like.model.FeedLikeRes;
import com.green.greengram.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final FeedLikeMapper feedLikeMapper;
    private final MyFileUtils myFileUtils;
    private final FeedLikeService feedLikeService;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = feedMapper.insFeed(p);

        // --------------- 파일 등록
        long feedId = p.getFeedId();

        //저장 폴더 만들기, 저장위치/feed/${feedId}/파일들을 저장한다.
        String middlePath = String.format("feed/%d", feedId);
        myFileUtils.makeFolders(middlePath);

        //랜덤 파일명 저장용  >> feed_pics 테이블에 저장할 때 사용
        List<String> picNameList = new ArrayList<>(pics.size());
        for (MultipartFile pic : pics) {
            //각 파일 랜덤파일명 만들기
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        feedPicDto.setPics(picNameList);
        int resultPics = feedPicMapper.insFeedPic(feedPicDto);

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

//    public void likeFeed(long feedId, long userId) {
//        // 좋아요 중복 확인
//        int count = feedMapper.checkLikeExists(feedId, userId);
//        if (count == 0) {
//            feedMapper.insertLike(feedId, userId); // 좋아요 추가
//        } else {
//            throw new IllegalStateException("이미 좋아요를 누른 상태입니다.");
//        }
//    }

    public List<FeedGetRes> getFeedList(FeedGetReq p) {
        // N + 1 이슈 발생
        List<FeedGetRes> list = feedMapper.selFeedList(p);
        log.info("listTest = {}", list.toString());
//

        for (int i = 0; i < list.size(); i++) {
            FeedGetRes item = list.get(i);
            //피드 당 사진 리스트
            item.setPics(feedPicMapper.selFeedPic(item.getFeedId()));

//            for (FeedGetRes item : list) {
//                // 피드 당 사진 리스트 설정
//                item.setPics(feedPicsMapper.selFeedPics(item.getFeedId()));
//            }

            //피드 당 댓글 4개
            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(item.getFeedId(), 0, 3);
            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(commentGetReq); //0, 4

            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size() == commentGetReq.getSize()); //4개면 true, 4개 아니면 false

            if (commentGetRes.isMoreComment()) {
                commentList.remove(commentList.size() - 1);
            }
            item.setComment(commentGetRes);
        }
        return list;
    }

//    public List<FeedGetRes> getFeedList2(FeedGetReq p) {
//
//        // 1. 피드 리스트 조회
//        List<FeedGetRes> list = feedMapper.selFeedList(p);
//
//        // 2. 피드 ID 목록 추출
//        List<Long> feedIds = list.stream()
//                .map(FeedGetRes::getFeedId)
//                .collect(Collectors.toList());
//
//        if (feedIds.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        // 3. 피드와 관련된 사진 리스트 조회
//        List<FeedPicSel> feedPics = feedPicMapper.selFeedPicListByFeedIds(feedIds);
//
//        // 4. 피드와 관련된 댓글 리스트 조회 (feedIds 전달)
//        List<FeedCommentSel> feedComments = feedCommentMapper.selFeedCommentListByFeedIdsLimit4(feedIds);
//
//        // 5. 피드 ID를 키로 댓글 리스트를 저장할 맵 생성
//        Map<Long, List<FeedCommentDto>> commentHashMap = new HashMap<>();
//        for (FeedCommentSel item : feedComments) {
//            FeedCommentDto dto = new FeedCommentDto();
//            dto.setFeedCommentId(item.getFeedCommentId());
//            dto.setComment(item.getComment());
//            dto.setWriterUserId(item.getWriterUserId());
//
//            commentHashMap.computeIfAbsent(item.getFeedId(), k -> new ArrayList<>()).add(dto);
//        }
//
//        // 6. 피드와 사진, 댓글 데이터 매핑
//        for (FeedGetRes res : list) {
//            // 사진 매핑
//            List<String> pics = feedPics.stream()
//                    .filter(pic -> pic.getFeedId() == res.getFeedId())
//                    .map(FeedPicSel::getPic)
//                    .collect(Collectors.toList());
//            res.setPics(pics);
//
//            // 댓글 매핑
//            List<FeedCommentDto> comments = commentHashMap.getOrDefault(res.getFeedId(), Collections.emptyList());
//            FeedCommentGetRes commentRes = new FeedCommentGetRes();
//            commentRes.setCommentList(comments);
//            commentRes.setMoreComment(false); // 추가 로직 필요 시 설정
//            res.setComment(commentRes);
//        }
//
//        return list;
//    }
    // select 3번, 피드 5000개 있음, 페이지 당 20개씩 피드 들고 온다.
    public List<FeedGetRes> getFeedList3(FeedGetReq p) {
        //피드 리스트
        List<FeedGetRes> list = feedMapper.selFeedList(p);

        //feed_id를 골라내야 한다.
        List<Long> feedIds4 = list.stream().map(FeedGetRes::getFeedId).collect(Collectors.toList());
        List<Long> feedIds5 = list.stream().map(item -> ((FeedGetRes)item).getFeedId()).toList();
        List<Long> feedIds6 = list.stream().map(item -> { return ((FeedGetRes)item).getFeedId();}).toList();

        List<Long> feedIds = new ArrayList<>(list.size());
        for(FeedGetRes item : list) {
            feedIds.add(item.getFeedId());
        }
        log.info("feedIds: {}", feedIds);

        //피드와 관련된 사진 리스트
        List<FeedPicSel> feedPicList = feedPicMapper.selFeedPicListByFeedIds(feedIds);
        log.info("feedPicList: {}", feedPicList);

        Map<Long, List<String>> picHashMap = new HashMap<>();
        for(FeedPicSel item : feedPicList) {
            long feedId = item.getFeedId();
            if(!picHashMap.containsKey(feedId)) {
                picHashMap.put(feedId, new ArrayList<String>(2));
            }
            List<String> pics = picHashMap.get(feedId);
            pics.add(item.getPic());
        }



//        int lastIndex = 0;
//        for(FeedGetRes res : list) {
//            List<String> pics = new ArrayList<>(2);
//            for(int i=lastIndex; i<feedPicList.size(); i++) {
//                FeedPicSel feedPicSel = feedPicList.get(i);
//                if(res.getFeedId() == feedPicSel.getFeedId()) {
//                    pics.add(feedPicSel.getPic());
//                } else {
//                    res.setPics(pics);
//                    lastIndex = i;
//                    break;
//                }
//            }
//        }

        //피드와 관련된 댓글 리스트
        List<FeedCommentDto> feedCommentList = feedCommentMapper.selFeedCommentListByFeedIdsLimit4(feedIds);
        Map<Long, FeedCommentGetRes> commentHashMap = new HashMap<>();
        for(FeedCommentDto item : feedCommentList) {
            long feedId = item.getFeedId();
            if(!commentHashMap.containsKey(feedId)) {
                FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                commentHashMap.put(feedId, feedCommentGetRes);
            }
            FeedCommentGetRes feedCommentGetRes = commentHashMap.get(feedId);
            feedCommentGetRes.getCommentList().add(item);
        }

        for(FeedGetRes res : list) {
            res.setPics(picHashMap.get(res.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentHashMap.get(res.getFeedId());

            if(feedCommentGetRes == null) {
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                res.setComment(feedCommentGetRes);
            } else if (feedCommentGetRes.getCommentList().size() == 4) {
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size() - 1);
            }
            res.setComment(feedCommentGetRes);
        }
        log.info("list: {}", list);
        return list;
    }


    @Transactional
    public int deleteFeed(FeedDelReq p) {
        return feedMapper.deleteFeed(p);
    }

   /*

      @Transactional
    public int deleteFeed(FeedDeleteReq p) {
        //피드 사진 삭제
        String deletePath = String.format("%s/feed/%d", myFileUtils.getUploadPath(), p.getFeedId());
        myFileUtils.deleteFolder(deletePath, true);

        //피드 댓글, 좋아요 삭제
        int affectedRows = feedMapper.delFeedLikeAndFeedCommentAndFeedPic(p);
        log.info("affectedRows: {}", affectedRows);

        //피드 삭제
        return feedMapper.delFeed(p);
    }

    */
  }



