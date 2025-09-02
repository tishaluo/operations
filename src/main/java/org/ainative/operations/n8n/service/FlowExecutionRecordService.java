package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSONObject;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.ainative.operations.n8n.repository.FlowExecutionRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlowExecutionRecordService {


    private final FlowExecutionRecordRepository flowExecutionRecordRepository;

    public FlowExecutionRecordService(FlowExecutionRecordRepository flowExecutionRecordRepository) {
        this.flowExecutionRecordRepository = flowExecutionRecordRepository;
    }

    public FlowExecutionRecord createRecord(FlowExecutionRecord flowExecutionRecord) {
        return flowExecutionRecordRepository.save(flowExecutionRecord);
    }

    public void updateTime(String executionId , LocalDateTime end) {
        flowExecutionRecordRepository.updateEndTimeByFlowId(end,executionId);
    }


}
