package com.technova.services;

import com.technova.dto.CompanyProduct.CompanyProductRequestDTO;
import com.technova.dto.CompanyProduct.CompanyProductResponseDTO;
import com.technova.models.CompanyProductsModel;
import com.technova.repositories.CompanyProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyProductService {

    private final CompanyProductsRepository repository;

    public CompanyProductResponseDTO create(CompanyProductRequestDTO dto) {
        CompanyProductsModel entity = mapToEntity(dto);
        return mapToResponse(repository.save(entity));
    }

    public List<CompanyProductResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<CompanyProductResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public CompanyProductResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public CompanyProductResponseDTO update(Long id, CompanyProductRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setImageUrl(dto.getImageUrl());
            existing.setProductHeading(dto.getProductHeading());
            existing.setRating(dto.getRating());
            existing.setSubscriberCount(dto.getSubscriberCount());
            existing.setDescription(dto.getDescription());
            existing.setKeyFeatures(dto.getKeyFeatures());
            existing.setTechnologies(dto.getTechnologies());
            existing.setPrice(dto.getPrice());
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

    public List<CompanyProductResponseDTO> search(String keyword) {
        return repository.searchByHeadingOrTechnologies(keyword).stream()
                .map(this::mapToResponse).toList();
    }

    // === Mapping ===

    private CompanyProductsModel mapToEntity(CompanyProductRequestDTO dto) {
        return new CompanyProductsModel(
                null,
                dto.getImageUrl(),
                dto.getProductHeading(),
                dto.getRating(),
                dto.getSubscriberCount(),
                dto.getDescription(),
                dto.getKeyFeatures(),
                dto.getTechnologies(),
                dto.getPrice()
        );
    }

    private CompanyProductResponseDTO mapToResponse(CompanyProductsModel entity) {
        CompanyProductResponseDTO dto = new CompanyProductResponseDTO();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setProductHeading(entity.getProductHeading());
        dto.setRating(entity.getRating());
        dto.setSubscriberCount(entity.getSubscriberCount());
        dto.setDescription(entity.getDescription());
        dto.setKeyFeatures(entity.getKeyFeatures());
        dto.setTechnologies(entity.getTechnologies());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}