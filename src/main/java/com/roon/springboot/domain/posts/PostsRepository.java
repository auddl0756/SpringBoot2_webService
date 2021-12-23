package com.roon.springboot.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "ORDER by p.id DESC")
    List<Posts> findAllDesc();

    @Query("SELECT p " +
            "FROM Posts  p " +
            "WHERE p.title LIKE CONCAT('%',:keyword,'%') ")
    Page<Posts> findByKeywordAndPaged(String keyword, Pageable pageable);
}
