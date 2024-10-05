package com.example.contestplatform.dto;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Long id;
    private Long userId;
    private Long contestId;
    private Long problemId;
    private String code;
    private String language;
    private String status;
    private String codeOutput;
}
