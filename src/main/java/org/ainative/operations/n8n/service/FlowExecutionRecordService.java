package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.ainative.operations.n8n.dao.TwitterFlowCountDto;
import org.ainative.operations.n8n.domain.FlowExecutionRecord;
import org.ainative.operations.n8n.repository.FlowExecutionRecordRepository;
import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.service.TwitterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlowExecutionRecordService {

    private final TwitterConfigService twitterConfigService;

    private final FlowExecutionRecordRepository flowExecutionRecordRepository;

    public FlowExecutionRecordService(FlowExecutionRecordRepository flowExecutionRecordRepository, TwitterConfigService twitterConfigService) {
        this.flowExecutionRecordRepository = flowExecutionRecordRepository;
        this.twitterConfigService = twitterConfigService;
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
