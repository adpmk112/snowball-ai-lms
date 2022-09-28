package com.ace.ai.admin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.dtomodel.BatchCustomChapterDTO;
import com.ace.ai.admin.repository.CustomChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterRepository;

@Service
public class CustomChapterService {
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    CustomChapterFileRepository customChapterFileRepository;

    public void createNewActivity(CustomChapter customChapter) {
        customChapterRepository.save(customChapter);
    }

    public int getChapterIdByNameAndBatchId(String chapterName,int batchId) {
        CustomChapter customChapter = customChapterRepository.findByNameAndBatchId(chapterName,batchId);
        return customChapter.getId();
    }

    public void saveCustomChapterFile(CustomChapterFile customChapterFile) {
        customChapterFileRepository.save(customChapterFile);
    }

    public List<CustomChapterFile> getCustomChapterFileListById(int customChapterId) {
        return customChapterFileRepository.findByCustomChapterIdAndDeleteStatus(customChapterId, false);
    }

    public CustomChapter getCustomChapterById(int customChapterId) {
        return customChapterRepository.findById(customChapterId).get();
    }

    public CustomChapterFile getCustomChapterFileById(int customChapterId) {
        return customChapterFileRepository.findById(customChapterId).get();
    }

    public void deleteCustomChapterFile(int customChapterFileId) {
        CustomChapterFile customChapterFile = customChapterFileRepository.findById(customChapterFileId).get();
        customChapterFile.setDeleteStatus(true);
        customChapterFileRepository.save(customChapterFile);
    }

    public void editCustomChapter(int customChapterId, String customChapterName) {
        CustomChapter customChapter = customChapterRepository.findById(customChapterId).get();
        customChapter.setName(customChapterName);
        customChapterRepository.save(customChapter);
    }

    public List<BatchCustomChapterDTO> getCustomChapterListByBatchId(int batchId) {
        

        List<BatchCustomChapterDTO> BatchCustomChapterDTOList = new ArrayList<BatchCustomChapterDTO>();
        List<CustomChapter> customChapterList = customChapterRepository.findByBatchIdAndDeleteStatus(batchId, false);

        for (CustomChapter customChapter : customChapterList) {
            BatchCustomChapterDTO batchCustomChapterDTO = new BatchCustomChapterDTO();
            batchCustomChapterDTO.setId(customChapter.getId());
            batchCustomChapterDTO.setName(customChapter.getName());
            if (customChapter.getStartDate() != null && customChapter.getEndDate() != null) {
                LocalDate startDate = LocalDate.parse(customChapter.getStartDate());
                LocalDate endDate = LocalDate.parse(customChapter.getEndDate());
                LocalDate now = LocalDate.now();
                boolean lessThan = startDate.isBefore(endDate);
                boolean equal = startDate.isEqual(endDate);
                batchCustomChapterDTO.setStart_date(startDate);
                batchCustomChapterDTO.setEnd_date(endDate);
                // if ((lessThan && endDate.isAfter(now) && startDate.isEqual(now))
                //         || (lessThan && endDate.isEqual(now) || startDate.isEqual(now))) {
                //             batchCustomChapterDTO.setStatus("In progress");

                // } else if ((lessThan && endDate.isBefore(now)) || (equal && endDate.isBefore(now))) {

                //     batchCustomChapterDTO.setStatus("Done");
                // } else {
                //     batchCustomChapterDTO.setStatus("Not Started");

                // }

                if((lessThan && endDate.isAfter(now)  && (startDate.isEqual(now) || startDate.isBefore(now))) ||  ( lessThan && endDate.isEqual(now)  || startDate.isEqual(now))){
                    batchCustomChapterDTO.setStatus("In progress");

                }
                else if((lessThan && endDate.isBefore(now)) || (equal && endDate.isBefore(now))){

                    batchCustomChapterDTO.setStatus("Done");
                }
                else{
                    batchCustomChapterDTO.setStatus("Upcoming");

                }

            } else {
                batchCustomChapterDTO.setStatus("Not added");

            }
            BatchCustomChapterDTOList.add(batchCustomChapterDTO);

        }

        return BatchCustomChapterDTOList;
    }

    public void save(CustomChapter customChapter){
        customChapterRepository.save(customChapter);
    }

   

}
