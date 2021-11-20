package com.Snippitz.snipzapp.repository;

import com.Snippitz.snipzapp.entity.Language;
import com.Snippitz.snipzapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
