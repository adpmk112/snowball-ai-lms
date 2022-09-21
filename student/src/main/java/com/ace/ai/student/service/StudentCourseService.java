package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Chapter;
import com.ace.ai.student.datamodel.ChapterBatch;
import com.ace.ai.student.datamodel.Comment;
import com.ace.ai.student.datamodel.CustomChapter;
import com.ace.ai.student.datamodel.Reply;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ChapterBatchDTO;
import com.ace.ai.student.dtomodel.StuCommentViewDTO;
import com.ace.ai.student.repository.BatchRepository;
import com.ace.ai.student.repository.ChapterBatchRepository;
import com.ace.ai.student.repository.ChapterRepository;
import com.ace.ai.student.repository.CommentRepository;
import com.ace.ai.student.repository.CourseRepository;
import com.ace.ai.student.repository.CustomChapterRepository;
import com.ace.ai.student.repository.StudentRepository;

@Service
public class StudentCourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ChapterBatchRepository chapterBatchRepository;
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    StudentRepository studentRepository;
    


    public List<ChapterBatchDTO> getChapterList(int batchId){
        List<ChapterBatch> chapterBatchList = chapterBatchRepository.findByBatchId(batchId);
        List<ChapterBatchDTO> chapterBatchDTOList = new ArrayList<>();
        for(ChapterBatch chapterBatch : chapterBatchList){
            ChapterBatchDTO chapterBatchDTO = new ChapterBatchDTO();
            Chapter chapter = chapterRepository.findById(chapterBatch.getChapter().getId()).get();
            chapterBatchDTO.setId(chapterBatch.getId());
            chapterBatchDTO.setName(chapter.getName());
            chapterBatchDTO.setChapterId(chapter.getId());
            chapterBatchDTO.setStartDate(chapterBatch.getStartDate());
            chapterBatchDTO.setEndDate(chapterBatch.getEndDate());
            chapterBatchDTO.setDeleteStatus(chapterBatch.getDeleteStatus());
            chapterBatchDTOList.add(chapterBatchDTO);
        }
       
        return chapterBatchDTOList;
    }

    public List<CustomChapter> getCustomChapterList(int batchId){
        List<CustomChapter> customChapterList = customChapterRepository.findByBatchId(batchId);
        return customChapterList;
    }

    public Student getStudentById(int stuId){
        return studentRepository.findById(stuId).get();
    }

    

   
}
