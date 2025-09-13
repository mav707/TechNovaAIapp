package com.technova.dto.Employee;

import lombok.Data;

@Data
public class EmployeeResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String position;
    private String department;
    private Double salary;
    private String profileImageUrl;

    private boolean coreTeam;
}
