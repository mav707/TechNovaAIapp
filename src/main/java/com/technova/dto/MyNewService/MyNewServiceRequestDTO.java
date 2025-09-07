package com.technova.dto.MyNewService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class MyNewServiceRequestDTO {

    @NotBlank(message = "Heading is required")
    private String heading;

    @NotNull(message = "Items list cannot be null")
    @Size(min = 1, message = "At least one item is required")
    private List<@NotBlank String> items;
}
