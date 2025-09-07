package com.technova.controllers;

import com.technova.dto.CompanyProduct.CompanyProductRequestDTO;
import com.technova.dto.CompanyProduct.CompanyProductResponseDTO;
import com.technova.services.CompanyProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class CompanyProductController {

    private final CompanyProductService service;

    @PostMapping("/create")
    public ResponseEntity<CompanyProductResponseDTO> create(@Valid @RequestBody CompanyProductRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyProductResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CompanyProductResponseDTO> getById(@PathVariable Long id) {
        CompanyProductResponseDTO responseDTO = service.getById(id);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyProductResponseDTO> update(@PathVariable Long id,
                                                            @Valid @RequestBody CompanyProductRequestDTO dto) {
        CompanyProductResponseDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok("Product deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyProductResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CompanyProductResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}