package com.son.DayCapsule.service;

import com.son.DayCapsule.domain.Comment;
import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CommentServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser() {
        User user = new User();
        user.setUsername("홍길동");
        user.setPassword(passwordEncoder.encode("1234"));
        userService.signup(user);
        return user;
    }

    public Long createPost(User user) {
        return postService.create(user, "안녕하세요", "Hello World", user.getUsername());
    }

    @Test
    public void 댓글생성() throws Exception {
        // given
        User user = createUser();
        Long post_id = createPost(user);
        Post post = postService.findOne(post_id, false);
        // when
        Long comment_id = commentService.comment(post, "댓글입니다", "철수");
        // then
        Comment findComment = commentService.findOne(comment_id);
        assertEquals("댓글입니다", findComment.getBody());
        assertEquals("철수", findComment.getWriter());
        assertEquals(1, post.getComments().size());
    }

    @Test
    public void 댓글수정() throws Exception {
        // given
        User user = createUser();
        Long post_id = createPost(user);
        Post post = postService.findOne(post_id, false);
        // when
        Long comment_id = commentService.comment(post, "댓글입니다", "철수");
        commentService.update(comment_id, "수정했습니다");
        // then
        Comment findComment = commentService.findOne(comment_id);
        assertEquals("수정했습니다", findComment.getBody());
    }

    @Test
    public void 댓글삭제() throws Exception {
        // given
        User user = createUser();
        Long post_id = createPost(user);
        Post post = postService.findOne(post_id, false);
        // when
        Long comment_id = commentService.comment(post, "댓글입니다", "철수");
        Comment findComment = commentService.findOne(comment_id);
        commentService.delete(findComment);
        // then
        Comment deletedComment = commentService.findOne(comment_id);
        assertNull(deletedComment);
    }

}