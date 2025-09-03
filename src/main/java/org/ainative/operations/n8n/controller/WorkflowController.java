package org.ainative.operations.n8n.controller;

import com.alibaba.fastjson.JSONObject;
import org.ainative.operations.n8n.service.WorkflowService;
import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.service.TwitterConfigService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("flow")
public class WorkflowController {

    private final WorkflowService workflowService;

    private final TwitterConfigService twitterConfigService;

    public WorkflowController(WorkflowService workflowService, TwitterConfigService twitterConfigService) {
        this.workflowService = workflowService;
        this.twitterConfigService = twitterConfigService;
    }


    @GetMapping
    public ResponseEntity<JSONObject> queryById(UUID id, Boolean active) {

        Optional<TwitterConfig> twitterConfig = twitterConfigService.findById(id);

        return twitterConfig.map(config -> ResponseEntity.ok(workflowService.getFlows(config.getUsername(), active))).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping("/setActivate/{id}")
    public ResponseEntity<Map<String, Boolean>> setActivate(@PathVariable("id") UUID id, @Param("flowName") String flowName) {
        TwitterConfig cfg = twitterConfigService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Config not found: " + id));
        String workflowName = cfg.getUsername() + "-" + flowName;
        return ResponseEntity.ok(Map.of("state", workflowService.toggleActiveByName(workflowName,true)));
    }

    @GetMapping("/setDeactivate/{id}")
    public ResponseEntity<Map<String, Boolean>> setDeactivate(@PathVariable("id") UUID id, @Param("flowName") String flowName) {
        TwitterConfig cfg = twitterConfigService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Config not found: " + id));
        String workflowName = cfg.getUsername() + "-" + flowName;
        return ResponseEntity.ok(Map.of("state", workflowService.toggleActiveByName(workflowName,false)));
    }

}
