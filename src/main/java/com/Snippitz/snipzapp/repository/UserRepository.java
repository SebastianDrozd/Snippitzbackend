package com.Snippitz.snipzapp.repository;

import com.Snippitz.snipzapp.entity.SnipUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<SnipUser, UUID> {
    public SnipUser findSnipUserBySnipUsername(String username);
    public boolean existsBySnipUsername(String username);

}
