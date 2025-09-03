package org.ainative.operations.n8n.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FlowDto {
    private String shortName;
    private String name;
    private Boolean active;
}
