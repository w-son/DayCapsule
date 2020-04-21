package com.son.DayCapsule.repository;

import com.son.DayCapsule.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//페이징 + 복잡한 검색 조건  관련 인터페이스
public interface PostIRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.body like %:body%")
    public List<Post> findAllByBodyContains(@Param("body") String body);

    @Query("select p from Post p where p.body like %:body%")
    public Page<Post> findAllByBodyContaining(@Param("body") String body, Pageable pageable);

}
