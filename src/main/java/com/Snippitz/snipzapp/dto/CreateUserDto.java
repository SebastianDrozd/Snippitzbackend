package com.Snippitz.snipzapp.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateUserDto {
    private Date createdAt;
    private String snipUsername;
    private String snipPassword;
}
