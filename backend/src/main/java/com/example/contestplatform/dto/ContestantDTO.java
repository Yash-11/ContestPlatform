package com.example.contestplatform.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContestantDTO {

    private String name;
    private String username;
    private String password;
}
