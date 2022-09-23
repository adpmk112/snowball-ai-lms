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
import java.util.List;

public class AssignmentExcelExporter {
    private AssignmentReportDTO assignmentReportDTO;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    public AssignmentExcelExporter(AssignmentReportDTO assignmentReportDTO) {
        this.assignmentReportDTO = assignmentReportDTO;
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
        cell.setCellValue("Course : "+assignmentReportDTO.getCourseName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
        cell=row.createCell(1);
        cell.setCellValue("Batch : " +assignmentReportDTO.getBatchName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(1);

        row=sheet.createRow(1);
        cell= row.createCell(0);
        cell.setCellValue("Assignments");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
        List<Student> studentList = assignmentReportDTO.getStudentList();
        int cellCount = 1;
        if (studentList != null) {
            for (Student s : studentList) {
                cell = row.createCell(cellCount);
                cell.setCellValue(s.getName());
                cell.setCellStyle(style);
                sheet.autoSizeColumn(cellCount);
                cellCount++;
            }
        }
    }

    public void writeDataRows() {
        List<AssignmentMarkDTO> assignmentMarkDTOList = assignmentReportDTO.getStudentAssignmentMarks();
        int rowCount = 2;
        if (assignmentMarkDTOList.size() != 0) {
            for (AssignmentMarkDTO assignmentMarkDTO : assignmentMarkDTOList) {
                int cellCount = 0;
                Row row = sheet.createRow(rowCount++);
                Cell cell = row.createCell(cellCount++);
                cell.setCellValue(assignmentMarkDTO.getAssignment().getName());
                for (StudentIdMarkFilePathDTO s : assignmentMarkDTO.getStudentData()) {
                    cell = row.createCell(cellCount++);
                    cell.setCellValue(s.getMark());
                }
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
