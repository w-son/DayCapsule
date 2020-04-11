package com.son.DayCapsule.service;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser() {
        User user = new User();
        user.setUsername("홍길동");
        user.setPassword(passwordEncoder.encode("1234"));
        return user;
    }

    @Test
    public void 게시글생성() throws Exception {
        // given
        User user = createUser();
        userService.signup(user);
        Post post = Post.createPost(user, "게시글 제목", "게시글 내용", user.getUsername());
        // when
        Long id = postService.create(post);
        // then
        Post findPost = postService.findOne(id);
        assertEquals(post, findPost);
        assertEquals(0, findPost.getViewCount());
    }

    @Test
    public void 게시글수정() throws Exception {
        // given
        User user = createUser();
        userService.signup(user);
        Post post = Post.createPost(user, "게시글 제목", "게시글 내용", user.getUsername());
        Long id = postService.create(post);
        // when
        postService.update(id, "안녕하세요", "제 이름은 홍길동입니다");
        // then
        Post findPost = postService.findOne(id);
        assertEquals("안녕하세요", findPost.getTitle());
        assertEquals("제 이름은 홍길동입니다", findPost.getBody());
    }

    @Test
    public void 게시글삭제() throws Exception {
        // given
        User user = createUser();
        userService.signup(user);
        Post post = Post.createPost(user, "게시글 제목", "게시글 내용", user.getUsername());
        Long id = postService.create(post);
        // when
        postService.delete(post);
        // then
        Post findPost = postService.findOne(id);
        assertEquals(null, findPost);
    }
}