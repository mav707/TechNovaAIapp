package com.technova.dto.CompanyService;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyServiceRequestDTO {

    @NotBlank(message = "Service heading is required")
    private String serviceHeading;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Pricing is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal pricing;

    @NotNull(message = "Delivery time is required")
    @Min(value = 1, message = "Delivery must be at least 1 day")
    private Integer delivery;

    @NotNull(message = "Technologies list cannot be null")
    @Size(min = 1, message = "At least one technology is required")
    private List<@NotBlank String> technologies;

    @NotNull(message = "Key features list cannot be null")
    @Size(min = 1, message = "At least one key feature is required")
    private List<@NotBlank String> keyFeatures;

    @NotBlank(message = "Image URI is required")  // New validation added
    private String imageUri;
}
