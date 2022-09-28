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
        List<ChapterBatch> chapterBatches=chapterBatchRepository.findByBatchIdAndDeleteStatus(id,0);

        for(ChapterBatch chapterBatch:chapterBatches){
            ChapterDTO chapterDTO=new ChapterDTO();
            chapterDTO.setId(chapterBatch.getChapter().getId());
            chapterDTO.setName( chapterBatch.getChapter().getName());
            String startDate1=chapterBatch.getStartDate();
            String endDate1=chapterBatch.getEndDate();
            if(startDate1!=null && endDate1!=null){
                LocalDate startDate=LocalDate.parse(chapterBatch.getStartDate());
                LocalDate endDate=LocalDate.parse(chapterBatch.getEndDate());
                LocalDate now=LocalDate.now();
                boolean lessThan=startDate.isBefore(endDate);
                boolean equal=startDate.isEqual(endDate);
               chapterDTO.setStart_date(startDate);
                chapterDTO.setEnd_date(endDate);
                if((lessThan && endDate.isAfter(now)  && (startDate.isEqual(now) || startDate.isBefore(now))) ||  ( lessThan && endDate.isEqual(now)  || startDate.isEqual(now))){
                    chapterDTO.setStatus("In progress");

                }
                else if((lessThan && endDate.isBefore(now)) || (equal && endDate.isBefore(now))){

                    chapterDTO.setStatus("Done");
                }
                else{
                    chapterDTO.setStatus("Upcoming");

                }


            }
            else{
                chapterDTO.setStatus("Not added");

            }
            chapterDTOList.add(chapterDTO);


        }

         return chapterDTOList;
    }

    public boolean saveDatesForChapter(Integer chpId,String chpName,String startDate,String endDate,int batchId){

        Batch batch=batchRepository.findBatchById(batchId);
        ChapterBatch chapter_batch=chapterBatchRepository.findByChapterIdAndBatchIdAndDeleteStatus(chpId,batch.getId(),0);
        chapter_batch.setStartDate(startDate);
        chapter_batch.setEndDate(endDate);
        System.out.println("*******"+chapter_batch.getStartDate()+" :"+ chapter_batch.getEndDate());
        chapterBatchRepository.save(chapter_batch);
        return true;
    }
}
