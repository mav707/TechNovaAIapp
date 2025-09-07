package com.technova.controllers;

import com.technova.dto.CompanyService.CompanyServiceRequestDTO;
import com.technova.dto.CompanyService.CompanyServiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import com.technova.services.CompanyServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class CompanyServiceController {

    private final CompanyServiceService service;

    @PostMapping("/create")
    public ResponseEntity<CompanyServiceResponseDTO> create(@Valid @RequestBody CompanyServiceRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyServiceResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CompanyServiceResponseDTO> getById(@PathVariable Long id) {
        CompanyServiceResponseDTO result = service.getById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyServiceResponseDTO> update(@PathVariable Long id,
                                                            @Valid @RequestBody CompanyServiceRequestDTO dto) {
        CompanyServiceResponseDTO result = service.update(id, dto);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok("Deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyServiceResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }

    // ✅ Pagination endpoint
    @GetMapping("/page")
    public ResponseEntity<Page<CompanyServiceResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}


