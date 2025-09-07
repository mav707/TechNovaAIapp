package com.technova.controllers;

import com.technova.dto.MyNewService.MyNewServiceRequestDTO;
import com.technova.dto.MyNewService.MyNewServiceResponseDTO;
import com.technova.services.MyNewServiceService;
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
@RequestMapping("/api/v1/my-new-services")
@RequiredArgsConstructor
public class MyNewServiceController {

    private final MyNewServiceService service;

    @PostMapping("/create")
    public ResponseEntity<MyNewServiceResponseDTO> create(@Valid @RequestBody MyNewServiceRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MyNewServiceResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MyNewServiceResponseDTO> getById(@PathVariable Long id) {
        MyNewServiceResponseDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MyNewServiceResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody MyNewServiceRequestDTO dto) {
        MyNewServiceResponseDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok("Service deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MyNewServiceResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}
