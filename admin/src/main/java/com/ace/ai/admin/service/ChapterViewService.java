package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterBatch;
import com.ace.ai.admin.dtomodel.ChapterDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ChapterBatchRepository;
import com.ace.ai.admin.repository.ChapterRepository;
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
            LocalDate startDate=LocalDate.parse(chapterBatch.getStartDate());
            LocalDate endDate=LocalDate.parse(chapterBatch.getEndDate());
            Date currentDate=new Date();
            System.out.println(currentDate.getTime());
            if(chapterBatch.getStartDate().equals("") || chapterBatch.getEndDate().equals("")){
                chapterDTO.setStatus("Not added");

            }
            else{

                chapterDTO.setStart_date(startDate);
                chapterDTO.setEnd_date(endDate);
                if(startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())){
                    chapterDTO.setStatus("In progress");

                }
                else if(endDate.isBefore(LocalDate.now())){
                    System.out.println(endDate.isBefore(LocalDate.now()));
                    chapterDTO.setStatus("Done");
                }
                else{
                    chapterDTO.setStatus("Not Started");
                   
                }

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
