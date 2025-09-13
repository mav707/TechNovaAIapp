package com.technova.controllers;

import com.technova.dto.Employee.EmployeeRequestDTO;
import com.technova.dto.Employee.EmployeeResponseDTO;
import com.technova.services.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
        EmployeeResponseDTO result = service.getById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeRequestDTO dto) {
        EmployeeResponseDTO result = service.update(id, dto);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok("Deleted") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<EmployeeResponseDTO>> createBulk(@Valid @RequestBody List<EmployeeRequestDTO> dtoList) {
        return new ResponseEntity<>(service.createBulk(dtoList), HttpStatus.CREATED);
    }

    // ✅ Core team endpoint
    @GetMapping("/core-team")
    public ResponseEntity<List<EmployeeResponseDTO>> getCoreTeam() {
        return ResponseEntity.ok(service.getCoreTeam());
    }
}
