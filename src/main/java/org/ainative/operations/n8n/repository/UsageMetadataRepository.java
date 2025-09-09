package org.ainative.operations.n8n.repository;

import org.ainative.operations.n8n.domain.UsageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageMetadataRepository extends JpaRepository<UsageMetadata, Long> {

    List<UsageMetadata> findByExecutionId(Long executionId);

}
