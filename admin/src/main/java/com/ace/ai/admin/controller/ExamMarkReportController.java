package com.ace.ai.admin.controller;

import com.ace.ai.admin.dtomodel.AttendanceReportDTO;
import com.ace.ai.admin.dtomodel.ExamMarkReportDTO;
import com.ace.ai.admin.exporter.AttendanceExcelExporter;
import com.ace.ai.admin.exporter.ExamMarkExcelExporter;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamMarkReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/teacher/exam/exportToExcel{batch_id}")
    public void exportPDF(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime=dateFormatter.format(new Date());
        String fileName="exam_marks_"+currentDateTime+".xlsx";
        String headerValue="attachment; filename="+fileName;
        response.setHeader(headerKey,headerValue);
       ExamMarkReportDTO examMarkReportDTO = reportService.getStudentMarks(batchId);
        ExamMarkExcelExporter exporter  =new ExamMarkExcelExporter(examMarkReportDTO);
        exporter.export(response);

    }
}