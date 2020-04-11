package com.son.DayCapsule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String body;

    private String writer;

    private LocalDateTime postDate;

    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 생성 메서드
    public static Post createPost(User user, String title, String body, String writer) {
        Post post = new Post();
        user.addPost(post);

        post.setTitle(title);
        post.setBody(body);
        post.setWriter(writer);
        post.setPostDate(LocalDateTime.now());
        post.setViewCount(0);
        return post;
    }

    // 비즈니스 메서드
    public void addViewCount() {
        this.viewCount += 1;
    }

}
