package com.son.DayCapsule.repository;

import com.son.DayCapsule.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) { em.persist(post);}

    public void remove(Post post) { em.remove(post); }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        /*
         join fetch 를 통해서 매핑된 User도 한꺼번에 가져옴
         */
        return em.createQuery("select p from Post p" + " join fetch p.user u", Post.class)
                .getResultList();
    }

}
