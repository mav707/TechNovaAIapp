package com.technova.dto.CompanyProduct;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CompanyProductRequestDTO {

    private String imageUrl;

    @NotBlank(message = "Product heading is required")
    private String productHeading;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5")
    private Double rating;

    @NotNull(message = "Subscriber count is required")
    @Min(value = 0, message = "Subscriber count cannot be negative")
    private Integer subscriberCount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Key features list cannot be null")
    @Size(min = 1, message = "At least one key feature is required")
    private List<@NotBlank String> keyFeatures;

    @NotNull(message = "Technologies list cannot be null")
    @Size(min = 1, message = "At least one technology is required")
    private List<@NotBlank String> technologies;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double price;
}