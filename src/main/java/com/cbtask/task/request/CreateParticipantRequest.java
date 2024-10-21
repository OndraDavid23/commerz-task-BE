package com.cbtask.task.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateParticipantRequest {
    
    @NotNull(message="First name is mandatory.")
    @NotBlank(message="First name is mandatory.")
    public String firstName;
    @NotNull(message="Last name is mandatory.")
    @NotBlank(message="Last name is mandatory.")
    public String lastName;
    @NotNull(message="Age name is mandatory.")
    public int age;
}
