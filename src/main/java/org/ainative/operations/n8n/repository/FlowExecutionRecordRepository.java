package org.ainative.operations.n8n.repository;

import jakarta.transaction.Transactional;
import org.ainative.operations.n8n.dao.TwitterFlowCountDto;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlowExecutionRecordRepository extends JpaRepository<FlowExecutionRecord, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE FlowExecutionRecord f SET f.endTime = :endTime WHERE f.executionId = :executionId")
    void updateEndTimeByFlowId(@Param("endTime") LocalDateTime endTime,
                               @Param("executionId") String executionId);


    @Query("select new org.ainative.operations.n8n.dao.TwitterFlowCountDto(" +
            "f.twitterName, f.flowCommonName as serviceName, count(f)) " +
            "from FlowExecutionRecord f " +
            "where f.twitterName = :twitterName " +
            "group by f.twitterName, f.flowCommonName")
    List<TwitterFlowCountDto> countGroupByTwitterNameAndFlowName(@Param("twitterName") String twitterName);


}
