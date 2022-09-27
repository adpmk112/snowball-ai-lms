package com.ace.ai.admin.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ace.ai.admin.datamodel.*;
import com.ace.ai.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.dtomodel.AdminChapterDTO;
import com.ace.ai.admin.dtomodel.AdminCourseDTO;
import com.ace.ai.admin.dtomodel.ChapterFileDTO;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    ChapterFileRepository chapterFileRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ChapterBatchRepository chapterBatchRepository;

    public List<AdminCourseDTO> getCourseList() {
        List<Course> courseList = courseRepository.findAll();
        List<AdminCourseDTO> adminCourseDTOList = new ArrayList<>();
        for (Course course : courseList) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            AdminCourseDTO adminCourseDTO = new AdminCourseDTO();
            adminCourseDTO.setId(course.getId());
            adminCourseDTO.setName(course.getName());
            adminCourseDTO.setCreatedDate(LocalDate.parse(course.getCreatedDate(), df));
            adminCourseDTO.setDeleteStatus(course.isDeleteStatus());
            adminCourseDTOList.add(adminCourseDTO);
        }
        return adminCourseDTOList;
    }

    public List<AdminChapterDTO> getChapterList(int courseId) {
        List<Chapter> chapterList = chapterRepository.findByCourseIdAndDeleteStatus(courseId, 0);
        List<AdminChapterDTO> adminChapterDTOList = new ArrayList<>();
        for (Chapter chapter : chapterList) {

            AdminChapterDTO adminChapterDTO = new AdminChapterDTO();
            adminChapterDTO
                    .setTotalFile(chapterFileRepository.findByChapterIdAndDeleteStatus(chapter.getId(), 0).size());
            // adminChapterDTO.setcourseId(courseId);
            adminChapterDTO.setId(chapter.getId());
            adminChapterDTO.setName(chapter.getName());
            adminChapterDTO.setDeleteStatus(chapter.getDeleteStatus());
            adminChapterDTOList.add(adminChapterDTO);
        }
        return adminChapterDTOList;
    }

    public void saveFile(ChapterFile file) {
        // Save all the files into database

        chapterFileRepository.save(file);

    }

    public Chapter saveChapter(Chapter chapter) {

        return chapterRepository.save(chapter);
    }

    public List<Course> getAllCourse() {
        return courseRepository.findByDeleteStatus(false);

    }

    public List<AdminChapterDTO> getCourseDetail(int courseId) {
        List<AdminChapterDTO> chapterListDTO = new ArrayList<>();
        List<Chapter> chapterList = chapterRepository.findByCourseIdAndDeleteStatus(courseId, 0);
        for (Chapter chapter : chapterList) {
            AdminChapterDTO chapterDTO = new AdminChapterDTO();
            chapterDTO.setId(chapter.getId());
            chapterDTO.setName(chapter.getName());
            chapterListDTO.add(chapterDTO);
        }

        return chapterListDTO;
    }

    public List<ChapterFileDTO> getChapterFile(int chapterId) {
        List<ChapterFileDTO> chapterFileListDTO = new ArrayList<>();
        List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndDeleteStatus(chapterId, 0);
        for (ChapterFile chapterFile : chapterFileList) {
            ChapterFileDTO chapterFileDTO = new ChapterFileDTO();
            chapterFileDTO.setId(chapterFile.getId());
            chapterFileDTO.setName(chapterFile.getName());
            chapterFileDTO.setFileType(chapterFile.getFileType());
            chapterFileDTO.setDeleteStatus(chapterFile.getDeleteStatus());
            chapterFileListDTO.add(chapterFileDTO);
        }

        return chapterFileListDTO;
    }

    public ChapterFile getOneChapterFile(int chapterFileId) {
        ChapterFile chapterFile = chapterFileRepository.findById(chapterFileId);
        return chapterFile;
    }

    public void saveCourse(String courseName) {
        Course course = new Course();
        course.setName(courseName);
        course.setCreatedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        courseRepository.save(course);
    }

    public void editCourse(String courseName, int courseId) {
        Course course = courseRepository.findById(courseId).get();
        course.setName(courseName);
        courseRepository.save(course);
    }

    public void editChapter(int chapterId, String chapterName) {
        Chapter chapter = chapterRepository.findById(chapterId).get();
        chapter.setName(chapterName);
        chapterRepository.save(chapter);
    }

    // public void edit

    public int getChapterIdByNameAndCourseId(String chapterName,int courseId) {
        Chapter chapter = chapterRepository.findByNameAndCourseId(chapterName,courseId);
        return chapter.getId();
    }



    public int getChapterFileCount(int chapterId) {
        return chapterFileRepository.findByChapterIdAndDeleteStatus(chapterId, 0).size();
    }

    public Course getById(int id) {
        return courseRepository.getById(id);
    }

    public void deleteChapterFile(int chapterFileId) {
        ChapterFile chapterFile = chapterFileRepository.findById(chapterFileId);
        chapterFile.setDeleteStatus(1);
        chapterFileRepository.save(chapterFile);
    }

    public void deleteCourse(int courseId) {
        Course course = courseRepository.findById(courseId).get();
        course.setDeleteStatus(true);
        courseRepository.save(course);
        List<Chapter> chapterList = chapterRepository.findByCourseIdAndDeleteStatus(courseId, 0);
        for (Chapter chapter : chapterList) {
            chapter.setDeleteStatus(1);

            chapterRepository.save(chapter);
            List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndDeleteStatus(chapter.getId(),
                    0);
            for (ChapterFile chapterFile : chapterFileList) {
                chapterFile.setDeleteStatus(1);
                chapterFileRepository.save(chapterFile);
            }
        }

    }

    public void deleteChapter(int chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).get();
        chapter.setDeleteStatus(1);
        chapterRepository.save(chapter);
        List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndDeleteStatus(chapter.getId(), 0);
        for (ChapterFile chapterFile : chapterFileList) {
            chapterFile.setDeleteStatus(1);
            chapterFileRepository.save(chapterFile);
        }
    }

    public Chapter getChapterById(int chapterId) {
        return chapterRepository.findById(chapterId).get();
    }

    public List<Batch> getBatchListByCourseId(int courseId){
        return  batchRepository.findByCourseId(courseId);
    }

    public void saveChapterBatch(ChapterBatch chapterBatch){
         chapterBatchRepository.save(chapterBatch);
    }

    public ChapterBatch getChapterBatchByChapterIdAndBAtchId(int chapterId,int batchId){
        return chapterBatchRepository.findByChapterIdAndBatchIdAndDeleteStatus(chapterId, batchId, 0);
    }
}
