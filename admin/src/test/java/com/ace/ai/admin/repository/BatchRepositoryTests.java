package com.ace.ai.admin.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;


@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class BatchRepositoryTests{
@Autowired
private BatchRepository batchRepository;
@Autowired
private TestEntityManager entityManager;
@Test
public  void testCreateBatch(){
    Course course=new Course("Java","2022-01-01",false);
    Teacher teacher=new Teacher("Moe Yee","photo","TECH001","11111",false);
    
    List<TeacherBatch> list=new ArrayList<TeacherBatch>();
   
    teacher.setTeacherBatches(list);

    Batch batch= new Batch();
    batch.setCreatedDate("2022-02-03");
    batch.setDeleteStatus(false);
    batch.setName("OJT Batch 1");
    batch.setCourse(course);
    batch.setTeacherBatches(list);
    TeacherBatch teacherBatches=new TeacherBatch(false,teacher,batch);
    list.add(teacherBatches);
    
    Batch batchReturn=batchRepository.save(batch);
    Batch existBatch=entityManager.find(Batch.class, batchReturn.getId());
    assertEquals(existBatch.getId(),batch.getId());


}
}