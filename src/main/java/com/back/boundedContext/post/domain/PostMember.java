package com.back.boundedContext.post.domain;


import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "POST_MEMBER")
@Getter
public class PostMember extends ReplicaMember {
    public PostMember(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password, String nickname) {
        super(id, createDate, modifyDate, username, password, nickname);
    }
}