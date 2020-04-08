package com.son.DayCapsule.repository;

import com.son.DayCapsule.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        User findUser;
        try {
            findUser = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
        return findUser;
    }

    // One To Many Join Fetch
    public List<User> findAllV1() {
        return em.createQuery("select distinct u from User u" + " join fetch u.posts p", User.class)
                .getResultList();
    }

    // One To Many In Query Paging
    public List<User> findAllV2(int offset, int limit) {
        return em.createQuery("select u from User u" + " join fetch u.posts p", User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
