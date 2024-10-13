package com.example.contestplatform.dto;

import org.hibernate.mapping.List;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.model.Submission;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreDTO {
    private Long id;
    private String contestantName;
    private int noOfSubmission;
    private int rank;      
    private int score;
}
