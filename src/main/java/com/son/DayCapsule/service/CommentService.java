package com.son.DayCapsule.service;

import com.son.DayCapsule.domain.Comment;
import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long comment(Post post, String body, String writer) {
        Comment comment = Comment.createComment(post, body, writer);
        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    public List<Comment> findByPostId(Long id) {
        return commentRepository.findByPostId(id);
    }

    @Transactional
    public void update(Long id, String body) {
        Comment comment = commentRepository.findOne(id);
        comment.setBody(body);
        comment.setCommentTime(LocalDateTime.now());
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.remove(comment);
    }

}
