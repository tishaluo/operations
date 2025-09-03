package org.ainative.operations.n8n.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwitterFlowCountDto {
    private String twitterName;
    private String serviceName;
    private Long count;
}
