package com.back.boundedContext.Post.repository;

import com.back.boundedContext.Post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}