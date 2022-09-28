package com.ace.ai.admin.exporter;

import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.dtomodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class AttendanceExcelExporter {
    private AttendanceReportDTO attendanceReportDTO;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    public AttendanceExcelExporter(AttendanceReportDTO attendanceReportDTO) {
        this.attendanceReportDTO = attendanceReportDTO;
        workbook=new XSSFWorkbook();
        sheet=workbook.createSheet();
    }

    private void writeHeaderRow() {
        XSSFRow row = sheet.createRow(0);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Course : "+attendanceReportDTO.getCourseName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
        cell=row.createCell(1);
        cell.setCellValue("Batch : " +attendanceReportDTO.getBatchName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(1);


        row=sheet.createRow(1);
        cell= row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
       List<Student> students = attendanceReportDTO.getStudents();
        int cellCount = 1;
        if (students != null) {
            for (Student s : students) {
                cell = row.createCell(cellCount);
                cell.setCellValue(s.getName());
                cell.setCellStyle(style);
                sheet.autoSizeColumn(cellCount);
                cellCount++;
            }
        }
    }

    public void writeDataRows() {
        List<AttendanceDTO> attendanceDTOS = attendanceReportDTO.getAttendanceDTOS();
        List<StudentAttendanceDTO> studentDTOList=attendanceReportDTO.getStudentDTOList();
        int rowCount = 2;
        if (attendanceDTOS.size() != 0) {
            for (AttendanceDTO a : attendanceDTOS) {
                int cellCount = 0;
                Row row = sheet.createRow(rowCount++);
                Cell cell = row.createCell(cellCount++);
                cell.setCellValue(a.getDate());
                for (Integer map : a.getStudentAndAttend().keySet()) {
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(a.getStudentAndAttend().get(map));
                }
            }
            int cellCount = 0;
            Row row=sheet.createRow(rowCount);
            Cell cell=row.createCell(cellCount);
            cell.setCellValue("Total :");
            for(StudentAttendanceDTO s:studentDTOList){
                cell = row.createCell(++cellCount);
                cell.setCellValue(s.getAttendance());
            }

        }

    }
    public void export(HttpServletResponse response) throws IOException {
      writeHeaderRow();
      writeDataRows();
      ServletOutputStream outputStream =response.getOutputStream();
      workbook.write(outputStream);
      workbook.close();
      outputStream.close();

    }
}
