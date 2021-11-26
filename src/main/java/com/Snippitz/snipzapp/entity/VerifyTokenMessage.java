package com.Snippitz.snipzapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTokenMessage {
    private String tokenStatus;
    private String userName;
}
