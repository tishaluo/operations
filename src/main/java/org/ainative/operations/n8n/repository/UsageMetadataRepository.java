package org.ainative.operations.n8n.repository;

import org.ainative.operations.n8n.dao.UsageTokenSums;
import org.ainative.operations.n8n.domain.UsageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageMetadataRepository extends JpaRepository<UsageMetadata, Long> {

    List<UsageMetadata> findByExecutionId(String executionId);

    @Query("""
            select new org.ainative.operations.n8n.dao.UsageTokenSums(
                sum(u.promptTokenCount),
                sum(u.candidatesTokenCount),
                sum(u.totalTokenCount),
                sum(u.cachedContentTokenCount),
                sum(u.thoughtsTokenCount)
            )
            from UsageMetadata u
            where (:flowId is null or u.flowId = :flowId)
              and (:executionId is null or u.executionId = :executionId)
            """)
    UsageTokenSums sumFields(@Param("flowId") String flowId,
                             @Param("executionId") String executionId);

}
