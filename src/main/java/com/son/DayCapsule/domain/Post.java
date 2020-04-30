package com.son.DayCapsule.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // 생성 메서드
    public static Post createPost(User user, String title, String body, String writer) {
        Post post = new Post();

        post.setTitle(title);
        post.setBody(body);
        post.setWriter(writer);
        post.setPostDate(LocalDateTime.now());
        post.setViewCount(0);

        user.addPost(post);
        return post;
    }

    // 연관 관계 메서드
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    // 비즈니스 메서드
    public void addViewCount() {
        this.viewCount += 1;
    }

}
