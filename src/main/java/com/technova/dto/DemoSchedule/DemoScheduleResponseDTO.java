package com.technova.dto.DemoSchedule;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DemoScheduleResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String serviceInterest;
    private String projectDetails;
    private LocalDateTime preferredDemoTime;
}
