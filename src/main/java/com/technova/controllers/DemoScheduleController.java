package com.technova.controllers;

import com.lowagie.text.Phrase;
import com.technova.dto.DemoSchedule.DemoScheduleRequestDTO;
import com.technova.dto.DemoSchedule.DemoScheduleResponseDTO;
import com.technova.services.DemoScheduleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demo-schedule")
@RequiredArgsConstructor
public class DemoScheduleController {

    private final DemoScheduleService service;

    @PostMapping("/create")
    public ResponseEntity<DemoScheduleResponseDTO> create(@Valid @RequestBody DemoScheduleRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DemoScheduleResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DemoScheduleResponseDTO> getById(@PathVariable Long id) {
        DemoScheduleResponseDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DemoScheduleResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody DemoScheduleRequestDTO dto) {
        DemoScheduleResponseDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.ok("Demo schedule deleted")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demo schedule not found");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DemoScheduleResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=demo_schedules.xlsx");

        List<DemoScheduleResponseDTO> schedules = service.getAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Demo Schedules");

        Row header = sheet.createRow(0);
        String[] columns = {
                "ID", "First Name", "Last Name", "Email", "Phone", "Job Title",
                "Service Interest", "Project Details", "Preferred Time"
        };

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (DemoScheduleResponseDTO dto : schedules) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dto.getId());
            row.createCell(1).setCellValue(dto.getFirstName());
            row.createCell(2).setCellValue(dto.getLastName());
            row.createCell(3).setCellValue(dto.getEmail());
            row.createCell(4).setCellValue(dto.getPhoneNumber());
            row.createCell(5).setCellValue(dto.getJobTitle());
            row.createCell(6).setCellValue(dto.getServiceInterest());
            row.createCell(7).setCellValue(dto.getProjectDetails());
            row.createCell(8).setCellValue(dto.getPreferredDemoTime().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=demo_schedules.pdf");

        List<DemoScheduleResponseDTO> schedules = service.getAll();
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Demo Schedules"));
        document.add(new Paragraph(" ")); // Line break

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        String[] headers = {"Name", "Email", "Phone", "Service Interest", "Preferred Time"};
        for (String header : headers) {
            table.addCell(new PdfPCell(new Phrase(header)));
        }

        for (DemoScheduleResponseDTO dto : schedules) {
            table.addCell(dto.getFirstName() + " " + dto.getLastName());
            table.addCell(dto.getEmail());
            table.addCell(dto.getPhoneNumber());
            table.addCell(dto.getServiceInterest());
            table.addCell(dto.getPreferredDemoTime().toString());
        }

        document.add(table);
        document.close();
    }


}
