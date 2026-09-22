package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberPolicy;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.exception.DomainException;
import com.back.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberSupport memberSupport;
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;

    /**
     * 회원을 가입시킨다.
     *
     * @param username
     * @param password
     * @param nickname
     * @return
     */
    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        findByUsername(username).ifPresent(m -> {
            throw new DomainException("409-1", "이미 존재하는 username 입니다.");
        });

        return memberJoinUseCase.join(username, password, nickname);
    }

    /**
     * 회원 수를 반환한다.
     * @return
     */
    @Transactional(readOnly = true)
    public long count() {
        return memberSupport.count();
    }

    /**
     * 비밀번호 입력 화면에 표시할 무작위 보안 안내 문구를 반환한다.
     * @return
     */
    public String getRandomSecureTip() {
        return memberGetRandomSecureTipUseCase.getRandomSecureTip();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberSupport.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(int id) {
        return memberSupport.findById(id);
    }
}

