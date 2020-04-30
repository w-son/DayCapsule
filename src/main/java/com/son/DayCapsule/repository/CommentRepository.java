package com.son.DayCapsule.repository;

import com.son.DayCapsule.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPostId(Long id) {
        return em.createQuery("select c from Comment c where c.post.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void remove(Comment comment) {
        em.remove(comment);
    }

}
