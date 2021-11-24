package com.Snippitz.snipzapp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReadUserDto {
    private UUID id;
    private String snipUsername;
    private String snipPassword;
}
