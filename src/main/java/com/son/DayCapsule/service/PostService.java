package com.son.DayCapsule.service;

import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.repository.PostIRepository;
import com.son.DayCapsule.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostIRepository postIRepository;

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

    /**
     * 1) 페이징을 사용하는 메서드
     * 2) 검색 조건(내용을 포함하는)이 포함된 메서드
     * 3) 검색 조건(내용을 포함하는)과 페이징을 사용하는 메서드
     **/
    public Page<Post> findAllByPage(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("postDate").descending());
        return postIRepository.findAll(pageable);
    }

    public List<Post> findAllByBodyContains(String body) {
        return postIRepository.findAllByBodyContains(body);
    }

    public Page<Post> findAllByBodyContaining(String body, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("postDate").descending());
        return postIRepository.findAllByBodyContaining(body, pageable);
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
