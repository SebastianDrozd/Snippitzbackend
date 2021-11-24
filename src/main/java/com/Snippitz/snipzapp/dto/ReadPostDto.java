package com.Snippitz.snipzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostDto {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String title;
    private String description;
    private String language;
    private long likes;
    private String author;
    private String code;

}
