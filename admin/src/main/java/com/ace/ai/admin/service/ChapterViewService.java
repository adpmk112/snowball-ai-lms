package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterBatch;
import com.ace.ai.admin.dtomodel.ChapterDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ChapterBatchRepository;
import com.ace.ai.admin.repository.ChapterRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Service
public class ChapterViewService {

    @Autowired
    ChapterBatchRepository chapterBatchRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    BatchRepository batchRepository;

    public List<ChapterDTO> findAllChapterInChapterBatchByBatchId(Integer id) throws ParseException {

        List<ChapterDTO> chapterDTOList=new ArrayList<ChapterDTO>();
        List<ChapterBatch> chapterBatches=chapterBatchRepository.findChapterIdByBatchId(id);

        for(ChapterBatch chapterBatch:chapterBatches){
            ChapterDTO chapterDTO=new ChapterDTO();
            chapterDTO.setName( chapterBatch.getChapter().getName());
            if(chapterBatch.getStartDate()!=null && chapterBatch.getEndDate()!=null){
                LocalDate startDate=LocalDate.parse(chapterBatch.getStartDate());
                LocalDate endDate=LocalDate.parse(chapterBatch.getEndDate());
                LocalDate now=LocalDate.now();
                boolean lessThan=startDate.isBefore(endDate);
                boolean equal=startDate.isEqual(endDate);
               chapterDTO.setStart_date(startDate);
                chapterDTO.setEnd_date(endDate);
                if((lessThan && endDate.isAfter(now)  && startDate.isEqual(now) ) ||  ( lessThan && endDate.isEqual(now)  || startDate.isEqual(now))){
                    chapterDTO.setStatus("In progress");

                }
                else if((lessThan && endDate.isBefore(now)) || (equal && endDate.isBefore(now))){

                    chapterDTO.setStatus("Done");
                }
                else{
                    chapterDTO.setStatus("Not Started");

                }


            }
            else{
                chapterDTO.setStatus("Not added");

            }
            chapterDTOList.add(chapterDTO);


        }

         return chapterDTOList;
    }

    public boolean saveDatesForChapter(String chpName,String startDate,String endDate,int batchId){
        Chapter chapter=chapterRepository.findByName(chpName);
        Batch batch=batchRepository.findBatchById(batchId);

        ChapterBatch chapter_batch=chapterBatchRepository.findChapterBatchByBatchIdAndChapterId(batch.getId(),chapter.getId());
        chapter_batch.setStartDate(startDate);
        chapter_batch.setEndDate(endDate);
        chapterBatchRepository.save(chapter_batch);

        return true;
    }
}
