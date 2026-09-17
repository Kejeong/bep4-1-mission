package com.back.shared.post.event;

import com.back.shared.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreatedEvent {  // 게시글 생성 사실과 관련 데이터를 담음
    private final PostDto post;
}