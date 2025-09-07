package com.technova.dto.MyNewService;

import lombok.Data;

import java.util.List;

@Data
public class MyNewServiceResponseDTO {

    private Long id;

    private String heading;

    private List<String> items;
}
