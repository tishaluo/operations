package org.ainative.operations.n8n.repository;

import jakarta.transaction.Transactional;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlowExecutionRecordRepository extends JpaRepository<FlowExecutionRecord, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE FlowExecutionRecord f SET f.endTime = :endTime WHERE f.executionId = :updateEndTimeByFlowId")
  void updateEndTimeByFlowId(@Param("endTime") LocalDateTime endTime,
                             @Param("flowId") String executionId);


}
