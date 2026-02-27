package com.example.studentmanager.controller;

import com.example.studentmanager.model.User;
import com.example.studentmanager.service.StudentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExcelController {

    @Autowired
    private StudentService studentService;

    /**
     * GET /api/export  (Admin only)
     * Downloads an Excel file of all students.
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> export() throws IOException {

        List<User> users = studentService.getAllRaw();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Students");

            // ── Header row ────────────────────────────────────────────────────
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] headers = {"ID", "Name", "Email", "Role"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 6000);
            }

            // ── Data rows ─────────────────────────────────────────────────────
            int rowNum = 1;
            for (User u : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(u.getId());
                row.createCell(1).setCellValue(u.getName());
                row.createCell(2).setCellValue(u.getEmail());
                row.createCell(3).setCellValue(u.getRole());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }
}
