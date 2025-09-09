package org.ainative.operations.n8n.service;

import ch.qos.logback.core.util.StringUtil;
import org.ainative.operations.n8n.dao.UsageTokenSums;
import org.ainative.operations.n8n.domain.UsageMetadata;
import org.ainative.operations.n8n.repository.UsageMetadataRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public List<UsageMetadata> findUsageMetadata(String flowId, String executionId) {


        UsageMetadata probe = UsageMetadata.builder().build();

        if (!StringUtil.isNullOrEmpty(executionId)) {
            probe.setExecutionId(executionId);
        }

        if (!StringUtil.isNullOrEmpty(flowId)) {
            probe.setFlowId(flowId);
        }

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths(
                        "id",
                        "promptTokenCount",
                        "candidatesTokenCount",
                        "totalTokenCount",
                        "cachedContentTokenCount",
                        "thoughtsTokenCount",
                        "modelVersion"
                );

        Example<UsageMetadata> example = Example.of(probe, matcher);
        return usageMetadataRepository.findAll(example);
    }

    public UsageTokenSums sumUsageMetadata(String flowId, String executionId) {
        // 统一把空字符串当作 null 处理，便于查询放开该条件
        String flow = (flowId == null || flowId.isBlank()) ? null : flowId;
        return usageMetadataRepository.sumFields(flow, executionId);
    }
}
