package com.ace.ai.admin.controller;


import com.ace.ai.admin.exporter.AttendanceExcelExporter;
import com.ace.ai.admin.dtomodel.AttendanceReportDTO;
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

@Controller
public class AttendanceReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/admin/exportToExcel{batch_id}")
    public void exportAttendanceAdmin(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime=dateFormatter.format(new Date());
        String fileName="attendance_"+currentDateTime+".xlsx";
        String headerValue="attachment; filename="+fileName;
        response.setHeader(headerKey,headerValue);
        AttendanceReportDTO attendanceReportDTO= reportService.getAttendance(batchId);
        AttendanceExcelExporter exporter  =new AttendanceExcelExporter(attendanceReportDTO);
        exporter.export(response);

    }
    @GetMapping("/teacher/exportToExcel{batch_id}")
    public void exportAttendanceTeacher(@PathVariable("batch_id") Integer batchId, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime=dateFormatter.format(new Date());
        String fileName="attendance_"+currentDateTime+".xlsx";
        String headerValue="attachment; filename="+fileName;
        response.setHeader(headerKey,headerValue);
        AttendanceReportDTO attendanceReportDTO= reportService.getAttendance(batchId);
        AttendanceExcelExporter exporter  =new AttendanceExcelExporter(attendanceReportDTO);
        exporter.export(response);

    }

}
