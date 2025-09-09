package org.ainative.operations.n8n.service;

import org.ainative.operations.n8n.dao.TwitterFlowCountDto;
import org.ainative.operations.n8n.domain.UsageMetadata;
import org.ainative.operations.n8n.repository.UsageMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageMetadataService {

    private final UsageMetadataRepository usageMetadataRepository;

    public UsageMetadataService(UsageMetadataRepository usageMetadataRepository) {
        this.usageMetadataRepository = usageMetadataRepository;
    }

    public UsageMetadata createUsageMetadata(UsageMetadata usageMetadata) {
        return usageMetadataRepository.save(usageMetadata);
    }


    public List<UsageMetadata> findUsageMetadata(Long executionId) {
        return usageMetadataRepository.findByExecutionId(executionId);
    }
}
