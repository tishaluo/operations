package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSONObject;
import org.ainative.operations.n8n.dao.TwitterFlowCountDto;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.ainative.operations.n8n.repository.FlowExecutionRecordRepository;
import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.service.TwitterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlowExecutionRecordService {

    @Autowired
    private TwitterConfigService twitterConfigService;

    private final FlowExecutionRecordRepository flowExecutionRecordRepository;

    public FlowExecutionRecordService(FlowExecutionRecordRepository flowExecutionRecordRepository) {
        this.flowExecutionRecordRepository = flowExecutionRecordRepository;
    }

    public FlowExecutionRecord createRecord(FlowExecutionRecord flowExecutionRecord) {
        return flowExecutionRecordRepository.save(flowExecutionRecord);
    }

    public void updateTime(FlowExecutionRecord flowExecutionRecord) {
        flowExecutionRecordRepository.updateEndTimeByFlowId(flowExecutionRecord.getEndTime(), flowExecutionRecord.getExecutionId());
    }


    public List<TwitterFlowCountDto> countGroupByTwitterNameAndFlowName(UUID id) {

        Optional<TwitterConfig> twitterConfig = twitterConfigService.findById(id);

        return flowExecutionRecordRepository.countGroupByTwitterNameAndFlowName(twitterConfig.get().getUsername());
    }


}
