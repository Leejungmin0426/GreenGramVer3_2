package com.green.greengram.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class FeedDelReq {
        @Schema(name = "feed_id")
        private Long feedId;

        @ConstructorProperties({"feed_id"})
        public FeedDelReq(Long feedId) {
            this.feedId = feedId;

        }
}
