package com.Snippitz.snipzapp.repository;

import com.Snippitz.snipzapp.entity.Post;
import com.Snippitz.snipzapp.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<UserLike,Long> {

    List<UserLike> findByPostIdAndUserId(UUID id, UUID userId);

}
