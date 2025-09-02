package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowService {


    public JSONObject getFlows(String tag, Boolean active) {

        OkHttpClient client = new OkHttpClient();

        String url = "http://31.97.131.76:5678/api/v1/workflows";
        if (tag != null) {
            url = url + "?tags=" + tag;
        }
        if (active != null) {
            url = url + "&active=" + active;
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-N8N-API-KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Yjg1YzcwZC02ZWEwLTRiYzQtYWM4Yi01ZGYxNzM0YjQ3MzciLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzU1NjYzNTcyfQ.3NsQ1t_4QIx8iFnQVTEx0U_t_RupRijbrdycbP2gGNg")
                .build();

        System.out.println(url);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonStr = response.body() != null ? response.body().string() : "{}";

            JSONObject data = JSON.parseObject(jsonStr);
            JSONArray dataArr = data.getJSONArray("data");

            JSONArray resultArr = new JSONArray();
            dataArr.forEach(o->{
                JSONObject obj = (JSONObject) o;

                String name = obj.getString("name");
                String shortName = "";
                if (name.startsWith(tag)){
                    shortName =   name.replaceFirst(tag+"_","");
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

    public boolean setActivate(String workflowName) {
        String projectId = "2TBrwKIbGeIdNf3R";
        List<String> list = getWorkflowId(projectId, workflowName);
        if (list.size() != 1) {
            return false;
        }
        OkHttpClient client = new OkHttpClient();
        // 创建JSON请求体
        String json = "{\"active\":" + true + "}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // 确保RequestBody不为null
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://31.97.131.76:5678/api/v1/workflows/" + list.get(0) + "/activate")
                .post(requestBody)
                .addHeader("X-N8N-API-KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Yjg1YzcwZC02ZWEwLTRiYzQtYWM4Yi01ZGYxNzM0YjQ3MzciLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzU1NjYzNTcyfQ.3NsQ1t_4QIx8iFnQVTEx0U_t_RupRijbrdycbP2gGNg")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                JSONObject resultJson = JSONObject.parseObject(response.body().string());
                return resultJson.containsKey("active");
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getWorkflowId(String projectId, String workflowName) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = "http://31.97.131.76:5678/api/v1/workflows?projectId=" + projectId +
                "&limit=10&name=" + workflowName;

        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .url(url)
                .get()
                .addHeader("X-N8N-API-KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Yjg1YzcwZC02ZWEwLTRiYzQtYWM4Yi01ZGYxNzM0YjQ3MzciLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzU1NjYzNTcyfQ.3NsQ1t_4QIx8iFnQVTEx0U_t_RupRijbrdycbP2gGNg")
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code: " + response);
            }

            assert response.body() != null;
            String responseBody = response.body().string();
            return getWorkflowIds(JSON.parseObject(responseBody));
        } catch (IOException e) {
            throw new RuntimeException("API call failed: " + e.getMessage(), e);
        }
    }

    private List<String> getWorkflowIds(JSONObject obj) {
        try {
            JSONArray dataArray = obj.getJSONArray("data");
            List<String> result = new ArrayList<>();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject workflow = dataArray.getJSONObject(i);
                if (workflow.containsKey("id")) {
                    result.add(workflow.getString("id"));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 如果没有找到返回null
    }

    public boolean setDeactivate(String workflowName) {
        String projectId = "2TBrwKIbGeIdNf3R";
        List<String> list = getWorkflowId(projectId, workflowName);
        if (list != null && list.size() > 1) {
            return false;
        }
        OkHttpClient client = new OkHttpClient();
        // 创建JSON请求体
        String json = "{\"active\":" + false + "}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // 确保RequestBody不为null
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://31.97.131.76:5678/api/v1/workflows/" + list.get(0) + "/deactivate")
                .post(requestBody)
                .addHeader("X-N8N-API-KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Yjg1YzcwZC02ZWEwLTRiYzQtYWM4Yi01ZGYxNzM0YjQ3MzciLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzU1NjYzNTcyfQ.3NsQ1t_4QIx8iFnQVTEx0U_t_RupRijbrdycbP2gGNg")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                JSONObject resultJson = JSONObject.parseObject(response.body().string());
                return resultJson.containsKey("active");
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
