package com.Snippitz.snipzapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadCommentDto {
    private long id;
    private String commentMessage;
    private Date dateCreated;
    private String snipUser;
}
