package org.ainative.operations.n8n.controller;

import com.alibaba.fastjson.JSONObject;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.ainative.operations.n8n.service.FlowExecutionRecordService;
import org.ainative.operations.twitter.entity.TwitterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("record")
public class FlowExecutionRecordController {

    private final FlowExecutionRecordService flowExecutionRecordService ;
    public FlowExecutionRecordController(FlowExecutionRecordService flowExecutionRecordService) {
        this.flowExecutionRecordService = flowExecutionRecordService;
    }

    @PostMapping
    public ResponseEntity<FlowExecutionRecord> createRecord(@RequestBody FlowExecutionRecord record) {

        return ResponseEntity.ok(flowExecutionRecordService.createRecord(record));

    }

    @PutMapping
    public ResponseEntity<Void> updateEndTime(String executionId, LocalDateTime endTime) {
       flowExecutionRecordService.updateTime(executionId,endTime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
