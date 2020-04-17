package com.son.DayCapsule.repository;

import com.son.DayCapsule.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostIRepository extends JpaRepository<Post, Long> {
    /*
     Pageable 실험 인터페이스
    */
}
