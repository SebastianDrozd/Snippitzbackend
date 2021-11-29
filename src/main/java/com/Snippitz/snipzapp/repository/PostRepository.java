package com.Snippitz.snipzapp.repository;

import com.Snippitz.snipzapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByLanguage(String language);

    List<Post> findAllByOrderByCreatedAtDesc();


}
