package org.ainative.operations.n8n.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.ResponseBody;
import org.ainative.operations.n8n.service.WorkflowService;
import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.ainative.operations.twitter.service.TwitterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<JSONObject> queryById(UUID id , Boolean active) {

        Optional<TwitterConfig> twitterConfig= twitterConfigService.findById(id );

        return twitterConfig.map(config -> ResponseEntity.ok(workflowService.getFlows(config.getUsername(), active))).orElseGet(() -> ResponseEntity.notFound().build());

    }


}
