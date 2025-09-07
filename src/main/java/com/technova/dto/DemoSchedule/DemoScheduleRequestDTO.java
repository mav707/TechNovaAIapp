package com.technova.dto.DemoSchedule;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DemoScheduleRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String jobTitle;

    @NotBlank(message = "Service/Product interest is required")
    private String serviceInterest;

    private String projectDetails;

    @NotNull(message = "Preferred demo time is required")
    private LocalDateTime preferredDemoTime;
}
