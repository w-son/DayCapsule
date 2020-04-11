package com.son.DayCapsule.service;

import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long create(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    public Post findOne(Long id) {
        Post post = postRepository.findOne(id);
        return post;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void update(Long id, String title, String body) {
        Post updatePost = postRepository.findOne(id);
        updatePost.setTitle(title);
        updatePost.setBody(body);
        updatePost.setPostDate(LocalDateTime.now());
    }

    @Transactional

    public void delete(Post post) {
        postRepository.remove(post);
    }


}