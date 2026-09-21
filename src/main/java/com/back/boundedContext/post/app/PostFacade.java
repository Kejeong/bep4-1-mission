package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.rsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostWriteUseCase postWriteUseCase;
    private final PostSupport postSupport;
    private final PostSyncMemberUseCase postSyncMemberUseCase;

    /**
     * 멤버 동기화
     * @param member
     * @return
     */
    @Transactional
    public PostMember syncMember(MemberDto member) {
        return postSyncMemberUseCase.syncMember(member);
    }

    /**
     * 글 작성
     * @param author
     * @param title
     * @param content
     * @return
     */
    @Transactional
    public RsData<Post> write(PostMember author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    /**
     * 글 카운트
     * @return
     */
    @Transactional(readOnly = true)
    public long count() {
        return postSupport.count();
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(int id) {
        return postSupport.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PostMember> findMemberByUsername(String username) {
        return postSupport.findMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Post> findByOrderByIdDesc() {
        return postSupport.findByOrderByIdDesc();
    }
}