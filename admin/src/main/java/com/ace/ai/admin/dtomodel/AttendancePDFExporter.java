package com.ace.ai.admin.dtomodel;

import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.repository.StudentRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class AttendancePDFExporter {
    private AttendanceReportDTO attendanceReportDTO;



    public AttendancePDFExporter(AttendanceReportDTO attendanceReportDTO) {
        this.attendanceReportDTO = attendanceReportDTO;
    }
    private void writeTableHeader(PdfPTable table){
        PdfPCell cell=new PdfPCell();
        cell.setBackgroundColor(Color.lightGray);
        cell.setPadding(5);
        Font font= FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);
        cell.setPhrase(new Phrase("Date",font));
        table.addCell(cell);
        HashMap<Integer,String> map=attendanceReportDTO.getStudentNames();
           if(map != null){
               for (Integer key : map.keySet()) {
                   System.out.println(key.toString() + " ==> " + map.get(key));
                       cell.setPhrase(new Phrase(map.get(key),font));
                       table.addCell(cell);


               }
           }


    }
    private void writeTableData(PdfPTable table){
        HashMap<Integer,String> map=attendanceReportDTO.getStudentAndAttend();
        List<String> dates=attendanceReportDTO.getDateList();
        for(String d:dates){
            table.addCell(d);
            if(map!=null){
                for(Integer key:map.keySet()){
                    table.addCell(map.get(key));
                }
            }
        }

    }
    public void export(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();

        Font font= FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.blue);
        font.setSize(16);
        document.add(new Paragraph("Attendance",font));
        document.add(new Paragraph("Course : "+attendanceReportDTO.getCourseName() +"\t \t" + "Batch : "+attendanceReportDTO.getBatchName(),font));
        if(attendanceReportDTO.getTeacherName()!=null){
            document.add(new Paragraph("Teacher : "+attendanceReportDTO.getTeacherName(),font));
        }

        if(attendanceReportDTO.getStudentAndAttend().size()!=0 && attendanceReportDTO.getStudentAndAttend().size()<8) {
            PdfPTable table = new PdfPTable(attendanceReportDTO.getStudentAndAttend().size()+1);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15);

            writeTableHeader(table);
            writeTableData(table);
            document.add(table);
        }
        else{
            PdfPTable table = new PdfPTable(attendanceReportDTO.getStudentAndAttend().size()+1);

            table.setWidthPercentage(100);
            table.setSpacingBefore(15);

            writeTableHeader(table);
            writeTableData(table);
            document.add(table);
        }
        document.close();



    }
}
