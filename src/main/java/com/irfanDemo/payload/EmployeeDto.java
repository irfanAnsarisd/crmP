package com.irfanDemo.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;

    @NotBlank
    @Size(min=3, message = "At least 3 chars  required")
    private String name;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    @Size(min=10, max=10, message = "Should be 10 digits.")
    private String mobile;
}
