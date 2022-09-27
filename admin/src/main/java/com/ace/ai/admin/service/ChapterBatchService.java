package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterBatch;
import com.ace.ai.admin.repository.ChapterBatchRepository;

@Service
public class ChapterBatchService {
    @Autowired
    ChapterBatchRepository chapterBatchRepository;

    public void chapterBatchPlus(int batchId, int chapterId){
        ChapterBatch chapterBatch = new ChapterBatch();
        
        Batch batch = new Batch();
        batch.setId(batchId);

        Chapter chapter = new Chapter();
        chapter.setId(chapterId);

        chapterBatch.setBatch(batch);
        chapterBatch.setChapter(chapter);

        chapterBatchRepository.save(chapterBatch);
    }
}
