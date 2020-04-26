package com.son.DayCapsule;

import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class SampleInsertion {

    private final InitService initService;

    @PostConstruct
    public void start() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void init() {
            User user = createUser("hong", "123");
            em.persist(user);
            for (int i = 1; i <= 25; i++) {
                Post post = createPost(user, i + "번째 제목", i + "번쨰 게시글", user.getUsername());
                em.persist(post);
            }
        }

        private User createUser(String username, String password) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password));
            return user;
        }

        private Post createPost(User user, String title, String body, String username) {
            Post post = Post.createPost(user, title, body, username);
            return post;
        }

    }

}
