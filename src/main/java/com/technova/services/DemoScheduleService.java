package com.technova.services;

import com.technova.dto.DemoSchedule.DemoScheduleRequestDTO;
import com.technova.dto.DemoSchedule.DemoScheduleResponseDTO;
import com.technova.models.DemoScheduleModel;
import com.technova.repositories.DemoScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoScheduleService {

    private final DemoScheduleRepository repository;
    private final EmailService emailService;

    public DemoScheduleResponseDTO create(DemoScheduleRequestDTO dto) {
        DemoScheduleModel saved = repository.save(mapToEntity(dto));
        DemoScheduleResponseDTO response = mapToResponse(saved);

        // Send email
        emailService.sendScheduleConfirmation(
                response.getEmail(),
                response.getFirstName(),
                response.getPreferredDemoTime().toString()
        );

        return response;
    }

    public List<DemoScheduleResponseDTO> getAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Page<DemoScheduleResponseDTO> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponse);
    }

    public DemoScheduleResponseDTO getById(Long id) {
        return repository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public DemoScheduleResponseDTO update(Long id, DemoScheduleRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            existing.setEmail(dto.getEmail());
            existing.setPhoneNumber(dto.getPhoneNumber());
            existing.setJobTitle(dto.getJobTitle());
            existing.setServiceInterest(dto.getServiceInterest());
            existing.setProjectDetails(dto.getProjectDetails());
            existing.setPreferredDemoTime(dto.getPreferredDemoTime());

            // Send email
            emailService.sendScheduleConfirmation(
                    existing.getEmail(),
                    existing.getFirstName(),
                    existing.getPreferredDemoTime().toString()
            );

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

    private DemoScheduleModel mapToEntity(DemoScheduleRequestDTO dto) {
        return new DemoScheduleModel(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getJobTitle(),
                dto.getServiceInterest(),
                dto.getProjectDetails(),
                dto.getPreferredDemoTime()
        );
    }

    private DemoScheduleResponseDTO mapToResponse(DemoScheduleModel model) {
        DemoScheduleResponseDTO dto = new DemoScheduleResponseDTO();
        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setPhoneNumber(model.getPhoneNumber());
        dto.setJobTitle(model.getJobTitle());
        dto.setServiceInterest(model.getServiceInterest());
        dto.setProjectDetails(model.getProjectDetails());
        dto.setPreferredDemoTime(model.getPreferredDemoTime());
        return dto;
    }
}
