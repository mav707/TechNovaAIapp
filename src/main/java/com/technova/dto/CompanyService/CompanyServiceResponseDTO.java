package com.technova.dto.CompanyService;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyServiceResponseDTO {

    private Long id;
    private String serviceHeading;
    private String description;
    private BigDecimal pricing;
    private Integer delivery;
    private List<String> technologies;
    private List<String> keyFeatures;
    private String imageUri;  // New field added
}
