package com.technova.services;

import com.technova.dto.MyNewService.MyNewServiceRequestDTO;
import com.technova.dto.MyNewService.MyNewServiceResponseDTO;
import com.technova.models.MyNewServiceModel;
import com.technova.repositories.MyNewServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyNewServiceService {

    private final MyNewServiceRepository repository;

    public MyNewServiceResponseDTO create(MyNewServiceRequestDTO dto) {
        return mapToResponse(repository.save(mapToEntity(dto)));
    }

    public List<MyNewServiceResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<MyNewServiceResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public MyNewServiceResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public MyNewServiceResponseDTO update(Long id, MyNewServiceRequestDTO dto) {
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

    private MyNewServiceModel mapToEntity(MyNewServiceRequestDTO dto) {
        return new MyNewServiceModel(null, dto.getHeading(), dto.getItems());
    }

    private MyNewServiceResponseDTO mapToResponse(MyNewServiceModel model) {
        MyNewServiceResponseDTO dto = new MyNewServiceResponseDTO();
        dto.setId(model.getId());
        dto.setHeading(model.getHeading());
        dto.setItems(model.getItems());
        return dto;
    }
}
