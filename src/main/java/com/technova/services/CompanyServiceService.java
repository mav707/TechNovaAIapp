package com.technova.services;

import com.technova.dto.CompanyService.CompanyServiceRequestDTO;
import com.technova.dto.CompanyService.CompanyServiceResponseDTO;
import com.technova.models.CompanyServicesModel;
import com.technova.repositories.CompanyServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceService {

    private final CompanyServiceRepository repository;

    public CompanyServiceResponseDTO create(CompanyServiceRequestDTO dto) {
        CompanyServicesModel entity = mapToEntity(dto);
        return mapToResponse(repository.save(entity));
    }

    public List<CompanyServiceResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<CompanyServiceResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public CompanyServiceResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public CompanyServiceResponseDTO update(Long id, CompanyServiceRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setServiceHeading(dto.getServiceHeading());
            existing.setDescription(dto.getDescription());
            existing.setPricing(dto.getPricing());
            existing.setDelivery(dto.getDelivery());
            existing.setTechnologies(dto.getTechnologies());
            existing.setKeyFeatures(dto.getKeyFeatures());
            existing.setImageUri(dto.getImageUri());  // <-- set imageUri on update
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

    public List<CompanyServiceResponseDTO> search(String keyword) {
        return repository.searchByHeadingOrTechnologies(keyword)
                .stream().map(this::mapToResponse).toList();
    }

    // === Mapping Methods ===

    private CompanyServicesModel mapToEntity(CompanyServiceRequestDTO dto) {
        return new CompanyServicesModel(
                null,
                dto.getServiceHeading(),
                dto.getDescription(),
                dto.getPricing(),
                dto.getDelivery(),
                dto.getTechnologies(),
                dto.getKeyFeatures(),
                dto.getImageUri()  // <-- add imageUri here
        );
    }

    private CompanyServiceResponseDTO mapToResponse(CompanyServicesModel entity) {
        CompanyServiceResponseDTO dto = new CompanyServiceResponseDTO();
        dto.setId(entity.getId());
        dto.setServiceHeading(entity.getServiceHeading());
        dto.setDescription(entity.getDescription());
        dto.setPricing(entity.getPricing());
        dto.setDelivery(entity.getDelivery());
        dto.setTechnologies(entity.getTechnologies());
        dto.setKeyFeatures(entity.getKeyFeatures());
        dto.setImageUri(entity.getImageUri());  // <-- include imageUri in response
        return dto;
    }
}
