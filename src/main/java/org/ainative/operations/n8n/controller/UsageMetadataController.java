package org.ainative.operations.n8n.controller;

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

    @GetMapping("/{executionId}")
    public ResponseEntity<List<UsageMetadata>> findUsageMetadata(@PathVariable Long executionId) {
        return ResponseEntity.ok(usageMetadataService.findUsageMetadata(executionId));
    }


}
