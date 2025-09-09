package org.ainative.operations.n8n.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usage_metadata")
public class UsageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "execution_id")
    private Long executionId;

    /** 提示词 tokens 计数 */
    @Column(name = "prompt_token_count")
    private Long promptTokenCount;

    /** 候选 tokens 计数 */
    @Column(name = "candidates_token_count")
    private Long candidatesTokenCount;

    /** 总 tokens 计数 */
    @Column(name = "total_token_count")
    private Long totalTokenCount;

    /** 缓存内容 tokens 计数 */
    @Column(name = "cached_content_token_count")
    private Long cachedContentTokenCount;

    /** 思维/推理 tokens 计数（如有） */
    @Column(name = "thoughts_token_count")
    private Long thoughtsTokenCount;

    /** 模型版本 */
    @Column(name = "model_version", length = 128)
    private String modelVersion;


}