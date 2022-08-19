package com.ace.ai.admin.repository;


import com.ace.ai.admin.datamodel.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
}
