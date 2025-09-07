package com.technova.controllers;

import com.technova.dto.MyNewProduct.MyNewProductRequestDTO;
import com.technova.dto.MyNewProduct.MyNewProductResponseDTO;
import com.technova.services.MyNewProductService;
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
@RequestMapping("/api/v1/my-new-products")
@RequiredArgsConstructor
public class MyNewProductController {

    private final MyNewProductService service;

    @PostMapping("/create")
    public ResponseEntity<MyNewProductResponseDTO> create(@Valid @RequestBody MyNewProductRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MyNewProductResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MyNewProductResponseDTO> getById(@PathVariable Long id) {
        MyNewProductResponseDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MyNewProductResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody MyNewProductRequestDTO dto) {
        MyNewProductResponseDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok("Product deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MyNewProductResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}
