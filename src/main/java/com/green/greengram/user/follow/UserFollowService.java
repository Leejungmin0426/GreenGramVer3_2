package com.green.greengram.user.follow;

import com.green.greengram.feed.like.model.FeedLikeReq;
import com.green.greengram.user.follow.model.UserFollowReq;
import com.green.greengram.user.model.UserSignInRes;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class UserFollowService {
    private final UserFollowMapper mapper;

    public int postUserFollow(UserFollowReq p) {

        try {
            int result = mapper.postUserFollow(p);
            // 데이터 삽입 시도\
            return result;


        } catch (DuplicateKeyException e){
            System.out.println("중복된 팔로우 관계입니다: " + p.getFromUserId() + " -> " + p.getToUserId());
            return 0;

        } catch (Exception e) {
            System.out.println("예기치 못한 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

        public int deleteUserFollow (UserFollowReq p){

            int result = mapper.deleteUserFollow(p);

            return result;
        }


    }


