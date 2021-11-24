package com.Snippitz.snipzapp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserDto {
    private String snipUsername;
    private String snipPassword;
}
