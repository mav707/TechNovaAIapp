package com.technova.services;

import com.technova.dto.Employee.EmployeeRequestDTO;
import com.technova.dto.Employee.EmployeeResponseDTO;
import com.technova.models.EmployeeModel;
import com.technova.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeResponseDTO create(EmployeeRequestDTO dto) {
        EmployeeModel entity = mapToEntity(dto);
        return mapToResponse(repository.save(entity));
    }

    public List<EmployeeResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<EmployeeResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public EmployeeResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(dto.getFullName());
            existing.setEmail(dto.getEmail());
            existing.setPosition(dto.getPosition());
            existing.setDepartment(dto.getDepartment());
            existing.setSalary(dto.getSalary());
            existing.setProfileImageUrl(dto.getProfileImageUrl());
            existing.setCoreTeam(dto.isCoreTeam()); // ✅ update core team flag
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

    public List<EmployeeResponseDTO> search(String keyword) {
        return repository.searchByKeyword(keyword)
                .stream().map(this::mapToResponse).toList();
    }

    public List<EmployeeResponseDTO> getCoreTeam() {
        return repository.findByCoreTeamTrue()
                .stream().map(this::mapToResponse).toList();
    }

    public List<EmployeeResponseDTO> createBulk(List<EmployeeRequestDTO> dtoList) {
        List<EmployeeModel> entities = dtoList.stream()
                .map(this::mapToEntity)
                .toList();
        List<EmployeeModel> saved = repository.saveAll(entities);
        return saved.stream().map(this::mapToResponse).toList();
    }

    // === Mapping ===
    private EmployeeModel mapToEntity(EmployeeRequestDTO dto) {
        return new EmployeeModel(
                null,
                dto.getFullName(),
                dto.getEmail(),
                dto.getPosition(),
                dto.getDepartment(),
                dto.getSalary(),
                dto.getProfileImageUrl(),
                dto.isCoreTeam()
        );
    }

    private EmployeeResponseDTO mapToResponse(EmployeeModel entity) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPosition(entity.getPosition());
        dto.setDepartment(entity.getDepartment());
        dto.setSalary(entity.getSalary());
        dto.setProfileImageUrl(entity.getProfileImageUrl());
        dto.setCoreTeam(entity.isCoreTeam());
        return dto;
    }
}
