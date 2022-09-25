package com.ace.ai.admin.controller;

import com.ace.ai.admin.dtomodel.AssignmentMarkDTO;
import com.ace.ai.admin.dtomodel.AssignmentReportDTO;
import com.ace.ai.admin.dtomodel.ExamMarkReportDTO;
import com.ace.ai.admin.exporter.AssignmentExcelExporter;
import com.ace.ai.admin.exporter.ExamMarkExcelExporter;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExamMarkReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/teacher/exam/exportToExcel{batch_id}")
    public void exportExcelExam(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
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
    @GetMapping("/teacher/assignment/exportToExcel{batch_id}")
    public void exportExcelAssignment(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime=dateFormatter.format(new Date());
        String fileName="assignment_marks_"+currentDateTime+".xlsx";
        String headerValue="attachment; filename="+fileName;
        response.setHeader(headerKey,headerValue);
        AssignmentReportDTO assignmentReportDTO = reportService.getAssigmentMarks(batchId);
        AssignmentExcelExporter exporter  =new AssignmentExcelExporter(assignmentReportDTO);
        exporter.export(response);

    }
    @GetMapping("/admin/exam/exportToExcel{batch_id}")
    public void exportExcelExamAdmin(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
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
    @GetMapping("/admin/assignment/exportToExcel{batch_id}")
    public void exportExcelAssignmentAdmin(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime=dateFormatter.format(new Date());
        String fileName="assignment_marks_"+currentDateTime+".xlsx";
        String headerValue="attachment; filename="+fileName;
        response.setHeader(headerKey,headerValue);
        AssignmentReportDTO assignmentReportDTO = reportService.getAssigmentMarks(batchId);
        AssignmentExcelExporter exporter  =new AssignmentExcelExporter(assignmentReportDTO);
        exporter.export(response);

    }
}
