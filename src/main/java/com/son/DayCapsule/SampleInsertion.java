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

            Post post1 = createPost(user, "오늘의 일기장 1", "날씨가 화창하다", user.getUsername());
            Post post2 = createPost(user, "오늘의 일기장 2", "날씨가 매우 덥다", user.getUsername());
            Post post3 = createPost(user, "오늘의 일기장 3", "비가 온다", user.getUsername());
            Post post4 = createPost(user, "오늘의 일기장 4", "눈이 내린다", user.getUsername());
            Post post5 = createPost(user, "오늘의 일기장 5", "날씨가 우중충하다", user.getUsername());
            Post post6 = createPost(user, "오늘의 일기장 6", "오늘은 굉장히 더운 날이다", user.getUsername());
            Post post7 = createPost(user, "오늘의 일기장 7", "오늘은 해가 짧은 날이다", user.getUsername());
            Post post8 = createPost(user, "오늘의 일기장 8", "날씨가 매우 춥다", user.getUsername());
            Post post9 = createPost(user, "오늘의 일기장 9", "날씨가 화창하다", user.getUsername());
            Post post10 = createPost(user, "오늘의 일기장 10", "밖에 비가 주룩주룩 내린다", user.getUsername());

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);
            em.persist(post9);
            em.persist(post10);
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
