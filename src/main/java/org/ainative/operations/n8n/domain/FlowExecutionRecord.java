package org.ainative.operations.n8n.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 流程执行记录实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flow_execution_record")
public class FlowExecutionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 对应 PostgreSQL BIGSERIAL
    private Long id;                // 自增主键

    @Column(name = "flow_id", nullable = false, length = 64)
    private String flowId;          // 流程ID

    @Column(name = "flow_name", nullable = false, length = 255)
    private String flowName;        // 流程的名字

    @Column(name = "execution_id", nullable = false, unique = true, length = 64)
    private String executionId;     // 执行记录的ID

    @Column(name = "flow_common_name")
    private String flowCommonName;  // 流程通用名字

    @Column(name = "twitter_name", length = 100)
    private String twitterName;     // Twitter 名字

    @Column(name = "start_time")
    private LocalDateTime startTime;// 开始时间

    @Column(name = "end_time")
    private LocalDateTime endTime;  // 结束时间
}