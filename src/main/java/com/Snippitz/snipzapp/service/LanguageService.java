package com.Snippitz.snipzapp.service;


import com.Snippitz.snipzapp.entity.Language;
import com.Snippitz.snipzapp.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }



    public List<Language> getAllLanguages(){
        return languageRepository.findAll();
    }

}
