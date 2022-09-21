package com.ace.ai.admin.controller;

import com.ace.ai.admin.dtomodel.AttendancePDFExporter;
import com.ace.ai.admin.dtomodel.AttendanceReportDTO;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AttendanceReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/admin/exportToPDF{batch_id}")
    public void exportPDF(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey="Content-Disposition";
        String headerValue="attachment; filename=attendance.pdf";
        response.setHeader(headerKey,headerValue);
        AttendanceReportDTO attendanceReportDTO= reportService.getAttendance(batchId);
        AttendancePDFExporter exporter  =new AttendancePDFExporter(attendanceReportDTO);
        exporter.export(response);

    }
}
