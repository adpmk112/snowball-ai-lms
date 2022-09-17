package com.ace.ai.admin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {

   List<Assignment> findByDeleteStatusAndBatchId(boolean deleteStatus , int batchId);
}
