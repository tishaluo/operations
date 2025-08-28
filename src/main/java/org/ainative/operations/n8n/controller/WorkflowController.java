package org.ainative.operations.n8n.controller;

import org.ainative.operations.n8n.service.WorkflowService;
import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("flow")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }


    @GetMapping
    public ResponseEntity<Object> queryById(String tag, Boolean active) {
        return ResponseEntity.ok(workflowService.getFlows(tag, active)
        );
    }

}
