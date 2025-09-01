package org.ainative.operations.n8n.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
