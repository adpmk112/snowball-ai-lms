package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Chapter_Batch;
import com.ace.ai.admin.dtomodel.ChapterDTO;
import com.ace.ai.admin.repository.ChapterRepository;
import com.ace.ai.admin.repository.Chapter_BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static java.util.Calendar.getInstance;

@Service
public class ChapterViewService {

    @Autowired
    Chapter_BatchRepository chapter_batchRepository;
    @Autowired
    ChapterRepository chapterRepository;

    public List<ChapterDTO> findAllChapterInChapterBatchByBatchId(Integer id) throws ParseException {

        List<ChapterDTO> chapterDTOList=new ArrayList<ChapterDTO>();
        List<Chapter_Batch> chapter_batches=chapter_batchRepository.findChapterIdByBatchId(id);




        for(Chapter_Batch chapter_batch:chapter_batches){
            ChapterDTO chapterDTO=new ChapterDTO();
            chapterDTO.setName( chapter_batch.getChapter().getName());
            LocalDate startDate=LocalDate.parse(chapter_batch.getStart_date());
            LocalDate endDate=LocalDate.parse(chapter_batch.getEnd_date());
            if(chapter_batch.getStart_date().equals("") || chapter_batch.getEnd_date().equals("")){
                chapterDTO.setStatus("Not added");

            }
            else{
                chapterDTO.setStart_date(startDate);
                chapterDTO.setEnd_date(endDate);
                if (endDate.isEqual(LocalDate.now())){
                    chapterDTO.setStatus("In progress");

                }
                else if(endDate.isBefore(LocalDate.now())){
                    chapterDTO.setStatus("Not Started");
                }
                else{
                    chapterDTO.setStatus("Done");
                   
                }

            }
            chapterDTOList.add(chapterDTO);


        }

         return chapterDTOList;
    }
}
