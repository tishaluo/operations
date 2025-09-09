package org.ainative.operations.n8n.controller;

import org.ainative.operations.n8n.dao.UsageTokenSums;
import org.ainative.operations.n8n.domain.UsageMetadata;
import org.ainative.operations.n8n.service.UsageMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usage")
public class UsageMetadataController {

    private final UsageMetadataService usageMetadataService;

    public UsageMetadataController(UsageMetadataService usageMetadataService) {
        this.usageMetadataService = usageMetadataService;
    }

    @PostMapping
    public ResponseEntity<UsageMetadata> createRecord(@RequestBody UsageMetadata usageMetadata) {
        return ResponseEntity.ok(usageMetadataService.createUsageMetadata(usageMetadata));
    }

    @GetMapping
    public ResponseEntity<List<UsageMetadata>> findUsageMetadata(
            @RequestParam(required = false) String flowId,
            @RequestParam(required = false) String executionId) {
        return ResponseEntity.ok(usageMetadataService.findUsageMetadata(flowId, executionId));
    }


    @GetMapping("/sum")
    public ResponseEntity<UsageTokenSums> sum(@RequestParam(required = false) String flowId,
                                              @RequestParam(required = false) String executionId) {
        return ResponseEntity.ok(usageMetadataService.sumUsageMetadata(flowId, executionId));
    }
}
