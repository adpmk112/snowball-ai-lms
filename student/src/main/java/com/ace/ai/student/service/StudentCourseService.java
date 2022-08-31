package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Chapter;
import com.ace.ai.student.datamodel.ChapterBatch;
import com.ace.ai.student.datamodel.CustomChapter;
import com.ace.ai.student.repository.BatchRepository;
import com.ace.ai.student.repository.ChapterBatchRepository;
import com.ace.ai.student.repository.ChapterRepository;
import com.ace.ai.student.repository.CourseRepository;
import com.ace.ai.student.repository.CustomChapterRepository;

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

    public List<Chapter> getChapterList(int batchId){
        List<ChapterBatch> chapterBatchList = chapterBatchRepository.findByBatchId(batchId);
        List<Chapter> chapterList = new ArrayList<>();
        for(ChapterBatch chapterBatch : chapterBatchList){
            Chapter chapter = chapterRepository.findById(chapterBatch.getChapter().getId()).get();
            chapterList.add(chapter);
        }
       
        return chapterList;
    }

    public List<CustomChapter> getCustomChapterList(int batchId){
        List<CustomChapter> customChapterList = customChapterRepository.findByBatchId(batchId);
        return customChapterList;
    }
}
