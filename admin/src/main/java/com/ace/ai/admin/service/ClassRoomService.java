package com.ace.ai.admin.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.dtomodel.ReqClassroomDTO;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ClassRoomRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassRoomService {

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    TeacherBatchRepository teacherBatchRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    TeacherProfileService teacherProfileService;

    public static String englishTime(String input)
            throws ParseException {

        // Format of the date defined in the input String
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");

        // Change the pattern into 24 hour format
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date time = null;
        String output = "";

        // Converting the input String to Date
        time = dateFormat.parse(input);

        // Changing the format of date
        // and storing it in
        // String
        output = format.format(time);
        return output;
    }

    public String twelveHourFormat(String time) throws ParseException {

        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
        final Date dateObj = sdf.parse(time);

        log.info(new SimpleDateFormat("hh:mm a").format(dateObj));
        return new SimpleDateFormat("hh:mm a").format(dateObj);
    }

    public String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public List<ClassroomDTO> showClassroomTable(Integer batchId) throws ParseException {

        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        List<Classroom> classroomList = classRoomRepository.findAllByBatchIdAndDeleteStatus(batchId, false);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Classroom classroom : classroomList) {
            ClassroomDTO classroomDTO = new ClassroomDTO();
            classroomDTO.setId(classroom.getId());
            classroomDTO.setDate(LocalDate.parse(classroom.getDate(), df));
            classroomDTO.setLink(classroom.getLink());
            classroomDTO.setStatus("");
            classroomDTO.setTeacherName(classroom.getTeacherName());

            classroomDTO.setStartTime(twelveHourFormat(classroom.getStartTime()));
            classroomDTO.setEndTime(twelveHourFormat(classroom.getEndTime()));

            classroomDTO.setStartDateTime(
                    LocalDateTime.parse(classroomDTO.getDate() + " " + classroom.getStartTime(), dtf));

            classroomDTO
                    .setEndDateTime(LocalDateTime.parse(classroomDTO.getDate() + " " + classroom.getEndTime(), dtf));

            if (classroomDTO.getStartDateTime().isAfter(LocalDateTime.now())) {
                classroomDTO.setStatus("Upcoming");
            }

            else if (LocalDateTime.now().isAfter(classroomDTO.getEndDateTime())) {
                classroomDTO.setStatus("Done");
            }

            else if (LocalDateTime.now().isAfter(classroomDTO.getStartDateTime())
                    && LocalDateTime.now().isBefore(classroomDTO.getEndDateTime())) {
                classroomDTO.setStatus("In Progress");
            }

            log.info(classroomDTO.getStatus());

            classroomDTOList.add(classroomDTO);
        }

        return classroomDTOList;
    }

    public List<ClassroomDTO> fetchTeacherListForClassroom(Integer batchId) {
        List<TeacherBatch> teacherNameList = teacherBatchRepository.findByBatchId(batchId);
        List<ClassroomDTO> classroomDTOforTeacherList = new ArrayList<>();

        for (TeacherBatch teacherBatch : teacherNameList) {
            ClassroomDTO classroomDTOforTeacher = new ClassroomDTO();

            classroomDTOforTeacher.setTeacherName(teacherBatch.getTeacher().getName());
            classroomDTOforTeacher.setBatchId(batchId);
            classroomDTOforTeacherList.add(classroomDTOforTeacher);
        }
//            if(classroomDTOforTeacherList!=null) {
//                log.info(classroomDTOforTeacherList.get(0).getTeacherName());
//            }
        return classroomDTOforTeacherList;
    }

    public ClassroomDTO getTeacherName(String code){
        Teacher teacher = teacherProfileService.findByCode(code);
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setTeacherName(teacher.getName());
        return classroomDTO;
    }

    public void createClassroom(ReqClassroomDTO reqClassroomDTO) throws ParseException {
        Classroom classroom = new Classroom();

        classroom.setDate(reqClassroomDTO.getDate());
        classroom.setLink(reqClassroomDTO.getLink());
        classroom.setBatch(batchRepository.findBatchById(reqClassroomDTO.getBatchId()));
        classroom.setTeacherName(reqClassroomDTO.getTeacherName());
        classroom.setStartTime(englishTime(reqClassroomDTO.getStartTime()));
        classroom.setEndTime(englishTime(reqClassroomDTO.getEndTime()));
        classRoomRepository.save(classroom);
        createAttendanceByClassroom(classroom);
    }

    public void createAttendanceByClassroom(Classroom classroom) {

        List<Student> students = studentRepository.findByBatch(classroom.getBatch());

        for (Student student : students) {
            Attendance attendance = new Attendance();
            attendance.setClassroom(classroom);
            attendance.setStudent(student);
            attendance.setAttend("Present");
            attendanceRepository.save(attendance);
        }
    }

    public Classroom fetchClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = classRoomRepository.findById(classroomDTO.getId()).get();
        return classroom;
    }

    public void editClassroom(ClassroomDTO classroomDTO) throws ParseException {

        Batch batch = new Batch();
        batch.setId(classroomDTO.getBatchId());

        Classroom classroom = new Classroom();
        classroom.setId(classroomDTO.getId());
        classroom.setDate(convertDateToString(classroomDTO.getDate()));
        classroom.setLink(classroomDTO.getLink());
        classroom.setTeacherName(classroomDTO.getTeacherName());
        classroom.setStartTime(englishTime(classroomDTO.getStartTime()));
        classroom.setEndTime(englishTime(classroomDTO.getEndTime()));
        classroom.setBatch(batch);
        classRoomRepository.save(classroom);
    }

    public void deleteClassroom(ClassroomDTO classroomDTO) throws ParseException {
    	
    	Batch batch = new Batch();
    	batch.setId(classroomDTO.getBatchId());
    	
    	Classroom classroom = new Classroom();
    	classroom.setId(classroomDTO.getId());
        classroom.setDate(convertDateToString(classroomDTO.getDate()));
        classroom.setLink(classroomDTO.getLink());
        classroom.setTeacherName(classroomDTO.getTeacherName());
        classroom.setStartTime(englishTime(classroomDTO.getStartTime()));
        classroom.setEndTime(englishTime(classroomDTO.getEndTime()));
        classroom.setBatch(batch);
        classroom.setDeleteStatus(true);
        classRoomRepository.save(classroom);
    }
}
