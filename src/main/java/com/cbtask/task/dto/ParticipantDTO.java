package com.cbtask.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
}