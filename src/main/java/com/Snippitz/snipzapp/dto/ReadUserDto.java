package com.Snippitz.snipzapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReadUserDto {
    private UUID id;
    private String snipUsername;
    private String snipPassword;
}
