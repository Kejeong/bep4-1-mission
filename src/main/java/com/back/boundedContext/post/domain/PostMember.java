package com.back.boundedContext.post.domain;


import com.back.boundedContext.member.domain.Member;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@Getter
public class PostMember extends ReplicaMember {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;

    public PostMember(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}