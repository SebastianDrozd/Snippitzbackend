package com.Snippitz.snipzapp.repository;

import com.Snippitz.snipzapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(UUID id);

    List<Comment> findByPostIdOrderByDateCreatedDesc(UUID id);
}
