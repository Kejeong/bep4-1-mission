package com.back.boundedContext.member.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.member.domain.SourceMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER_MEMBER")
@NoArgsConstructor
@Getter
public class Member extends SourceMember {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;

    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    /**
     * 활동점수 증가
     * @param amount
     * @return
     */
    public int increaseActivityScore(int amount) {
        setActivityScore(getActivityScore() + amount);

        return getActivityScore();
    }
}