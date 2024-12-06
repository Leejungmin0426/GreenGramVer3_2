package com.green.greengram.feed.like.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FeedLikeRes {

        private List<String> pics;
        private boolean isLiked;
    }

