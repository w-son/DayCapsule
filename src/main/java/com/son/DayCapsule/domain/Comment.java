package com.son.DayCapsule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String body;

    private String writer;

    private LocalDateTime commentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comment createComment(Post post, String body, String writer) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setWriter(writer);
        comment.setCommentTime(LocalDateTime.now());

        post.addComment(comment);
        return comment;
    }

}
