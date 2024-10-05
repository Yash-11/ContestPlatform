package com.example.contestplatform.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.io.Serializable;


@Entity
@Data
public class Submission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long contestId;
    private Long problemId;
    // @Column(columnDefinition = "TEXT")  // For MySQL, defines a large text column
    // @Lob  // Use Large Object type
    // @Column(length = 10000)
    // @Lob  // Defines the field as a large object
    // @Column(columnDefinition = "TEXT")  // Explicitly define as TEXT in H2
    // @Lob  // Defines this as a large object (Clob/Text)
    // @Column(columnDefinition = "CLOB")  // Use CLOB for large strings in H2
    // @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "CODE", length=1024)
    protected String code;
    private String language; // Python, C++, Java, etc.
    private String status; // SUCCESS, FAILURE, TIMEOUT, etc.
    // @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "CODEOUTPUT", length=1024)
    private String codeOutput;
}