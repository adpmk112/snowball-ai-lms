package com.ace.ai.admin.exporter;

import com.ace.ai.admin.dtomodel.AttendanceReportDTO;
import com.ace.ai.admin.dtomodel.ExamMarkReportDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class ExamMarkExcelExporter {
    private ExamMarkReportDTO examMarkReportDTO;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    public ExamMarkExcelExporter(ExamMarkReportDTO examMarkReportDTO) {
        this.examMarkReportDTO = examMarkReportDTO;
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
        cell.setCellValue("Course : "+examMarkReportDTO.getCourseName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
        cell=row.createCell(1);
        cell.setCellValue("Batch : " +examMarkReportDTO.getBatchName());
        cell.setCellStyle(style);
        sheet.autoSizeColumn(1);

        row=sheet.createRow(1);
        cell= row.createCell(0);
        cell.setCellValue("Name");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);
        List<String> examName = examMarkReportDTO.getExamName();
        int cellCount = 1;
        if (examName != null) {
            for (String s : examName) {
                cell = row.createCell(cellCount);
                cell.setCellValue(s);
                cell.setCellStyle(style);
                sheet.autoSizeColumn(cellCount);
                cellCount++;
            }
        }
    }

    public void writeDataRows() {
        List<String> studentList = examMarkReportDTO.getStudentName();
       List<Integer> markList=examMarkReportDTO.getStudentMark();
        if (examMarkReportDTO.getExamName().size() != 0) {
            int rowCount = 2;
            int cellCount = 0;
            if(studentList!=null) {
                for(String s : studentList) {
                    Row row = sheet.createRow(rowCount++);
                    Cell cell = row.createCell(cellCount++);
                    cell.setCellValue(s);
                    if (markList != null) {
                        for (Integer mark : markList) {
                            cell = row.createCell(cellCount++);
                            cell.setCellValue(mark);
                        }
                    }

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
