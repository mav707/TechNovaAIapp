package com.technova.services;

import com.technova.dto.MyNewProduct.MyNewProductRequestDTO;
import com.technova.dto.MyNewProduct.MyNewProductResponseDTO;
import com.technova.models.MyNewProductModel;
import com.technova.repositories.MyNewProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyNewProductService {

    private final MyNewProductRepository repository;

    public MyNewProductResponseDTO create(MyNewProductRequestDTO dto) {
        return mapToResponse(repository.save(mapToEntity(dto)));
    }

    public List<MyNewProductResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<MyNewProductResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public MyNewProductResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public MyNewProductResponseDTO update(Long id, MyNewProductRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setHeading(dto.getHeading());
            existing.setItems(dto.getItems());
            return mapToResponse(repository.save(existing));
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private MyNewProductModel mapToEntity(MyNewProductRequestDTO dto) {
        return new MyNewProductModel(null, dto.getHeading(), dto.getItems());
    }

    private MyNewProductResponseDTO mapToResponse(MyNewProductModel model) {
        MyNewProductResponseDTO dto = new MyNewProductResponseDTO();
        dto.setId(model.getId());
        dto.setHeading(model.getHeading());
        dto.setItems(model.getItems());
        return dto;
    }
}
