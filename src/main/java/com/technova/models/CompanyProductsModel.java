package com.technova.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CompanyProductsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String productHeading;

    private Double rating;

    private Integer subscriberCount;

    private String description;

    @ElementCollection
    private List<String> keyFeatures;

    @ElementCollection
    private List<String> technologies;

    private Double price;
}

