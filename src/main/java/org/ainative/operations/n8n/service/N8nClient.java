package org.ainative.operations.n8n.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class N8nClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String baseUrl;     // e.g. "http://31.97.131.76:5678"
    private final String apiKey;      // 建议改为从环境变量/配置读取
    private final OkHttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public N8nClient(String baseUrl, String apiKey) {
        this.baseUrl = stripTrailingSlash(baseUrl);
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 根据 projectId + workflowName 查询 workflowId 列表（最多 limit 个）
     */
    public List<String> findWorkflowIds(String projectId, String workflowName, int limit) {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/api/v1/workflows")).newBuilder()
                .addQueryParameter("projectId", projectId)
                .addQueryParameter("limit", String.valueOf(limit))
                .addQueryParameter("name", workflowName)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("X-N8N-API-KEY", apiKey)
                .get()
                .build();

        try (Response resp = client.newCall(request).execute()) {
            ensure2xx(resp);
            String body = resp.body() != null ? resp.body().string() : "{}";
            JsonNode root = mapper.readTree(body);
            JsonNode data = root.get("data");
            List<String> ids = new ArrayList<>();
            if (data != null && data.isArray()) {
                for (JsonNode n : data) {
                    JsonNode idNode = n.get("id");
                    if (idNode != null && !idNode.isNull()) {
                        ids.add(idNode.asText());
                    }
                }
            }
            return ids;
        } catch (IOException e) {
            throw new RuntimeException("findWorkflowIds failed: " + e.getMessage(), e);
        }
    }

    /**
     * 通过名称启用/停用（要求唯一匹配）
     */
    public boolean toggleActiveByName(String projectId, String workflowName, boolean active) {
        List<String> ids = findWorkflowIds(projectId, workflowName, 10);
        if (ids.size() != 1) return false;   // 明确只在唯一匹配时操作，避免误伤
        return toggleActiveById(ids.get(0), active);
    }

    /**
     * 通过 ID 启用/停用
     */
    public boolean toggleActiveById(String workflowId, boolean active) {
        // n8n 的 REST：POST /workflows/{id}/activate 或 /deactivate
        String path = active ? "/activate" : "/deactivate";
        HttpUrl url = HttpUrl.parse(baseUrl + "/api/v1/workflows/" + workflowId + path);

        // body 即使不需要，也给一个标准 JSON，便于网关/代理通过
        byte[] payload;
        try {
            payload = mapper.writeValueAsBytes(mapper.createObjectNode().put("active", active));
        } catch (Exception e) {
            payload = "{\"active\":false}".getBytes();
        }

        assert url != null;
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(payload, JSON))
                .header("X-N8N-API-KEY", apiKey)
                .header("Accept", "application/json")
                .build();

        try (Response resp = client.newCall(request).execute()) {
            ensure2xx(resp);
            String body = resp.body() != null ? resp.body().string() : "{}";
            JsonNode root = mapper.readTree(body);
            // 返回里若包含 active 字段，最好校验值
            JsonNode activeNode = root.get("active");
            return activeNode == null || activeNode.asBoolean() == active;
        } catch (IOException e) {
            throw new RuntimeException("toggleActiveById failed: " + e.getMessage(), e);
        }
    }

    private static void ensure2xx(Response resp) throws IOException {
        if (!resp.isSuccessful()) {
            String errBody = resp.body() != null ? resp.body().string() : "";
            throw new IOException("HTTP " + resp.code() + " - " + resp.message() + (errBody.isEmpty() ? "" : (" | " + errBody)));
        }
    }

    private static String stripTrailingSlash(String s) {
        if (s == null) return null;
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }

}
