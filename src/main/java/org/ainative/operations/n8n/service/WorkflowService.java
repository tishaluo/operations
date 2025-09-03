package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.ainative.operations.config.N8nConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WorkflowService {

    private final N8nConfig n8nConfig;

    public WorkflowService(N8nConfig n8nConfig) {
        this.n8nConfig = n8nConfig;
    }

    public JSONObject getFlows(String tag, Boolean active) {

        OkHttpClient client = new OkHttpClient();

        String url = n8nConfig.getBaseurl() + "/api/v1/workflows";
        if (tag != null) {
            url = url + "?tags=" + tag;
        }
        if (active != null) {
            url = url + "&active=" + active;
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(n8nConfig.getHeaderKey(), n8nConfig.getApikey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonStr = response.body() != null ? response.body().string() : "{}";

            JSONObject data = JSON.parseObject(jsonStr);
            JSONArray dataArr = data.getJSONArray("data");

            JSONArray resultArr = new JSONArray();
            dataArr.forEach(o -> {
                JSONObject obj = (JSONObject) o;

                String name = obj.getString("name");
                String shortName = "";
                if (name.startsWith(tag)) {
                    shortName = name.replaceFirst(tag + "_", "");
                }
                obj.put("shortName", shortName);
                resultArr.add(o);

            });
            data.put("data", resultArr);
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Request failed", e);
        }
    }

    public boolean toggleActiveByName(String workflowName, boolean active) {
        var n8n = new N8nClient(n8nConfig.getBaseurl(), n8nConfig.getApikey());
        return n8n.toggleActiveByName(n8nConfig.getProjectId(), workflowName, active);
    }


}
