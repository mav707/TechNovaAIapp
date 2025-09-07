package com.technova.dto.CompanyProduct;

import lombok.Data;

import java.util.List;

@Data
public class CompanyProductResponseDTO {

    private Long id;

    private String imageUrl;

    private String productHeading;

    private Double rating;

    private Integer subscriberCount;

    private String description;

    private List<String> keyFeatures;

    private List<String> technologies;

    private Double price;
}
