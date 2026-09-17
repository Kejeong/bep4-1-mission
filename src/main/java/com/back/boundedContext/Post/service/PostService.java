package com.back.boundedContext.Post.service;

import com.back.boundedContext.member.entity.Member;
import com.back.boundedContext.Post.entity.Post;
import com.back.boundedContext.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    public Post write(Member author, String title, String content) {
        Post post = new Post(author, title, content);

        author.increaseActivityScore(3);

        return postRepository.save(post);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
}