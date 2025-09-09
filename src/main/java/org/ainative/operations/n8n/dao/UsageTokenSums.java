package org.ainative.operations.n8n.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageTokenSums {
    private Long promptTokenSum;
    private Long candidatesTokenSum;
    private Long totalTokenSum;
    private Long cachedContentTokenSum;
    private Long thoughtsTokenSum;
}

