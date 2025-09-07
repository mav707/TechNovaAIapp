package com.technova.dto.MyNewProduct;

import lombok.Data;

import java.util.List;

@Data
public class MyNewProductResponseDTO {

    private Long id;

    private String heading;

    private List<String> items;
}
