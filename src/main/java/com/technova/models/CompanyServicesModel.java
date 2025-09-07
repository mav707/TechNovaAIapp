package com.technova.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CompanyServicesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceHeading;
    private String description;
    private BigDecimal pricing;
    private Integer delivery;

    @ElementCollection
    private List<String> technologies;

    @ElementCollection
    private List<String> keyFeatures;

    private String imageUri;  // New field added
}
