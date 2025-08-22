package org.ainative.operations.twitter.controller;

import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.ainative.operations.twitter.service.TwitterInteractionsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;


/**
 * (TwitterInteractions)表控制层
 *
 * @author makejava
 * @since 2025-08-21 15:55:20
 */
@RestController
@RequestMapping("twitterInteractions")
public class TwitterInteractionsController {
    /**
     * 服务对象
     */
    private final TwitterInteractionsService twitterInteractionsService;

    public TwitterInteractionsController(TwitterInteractionsService twitterInteractionsService) {
        this.twitterInteractionsService = twitterInteractionsService;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<List<TwitterInteractions>> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.twitterInteractionsService.queryById(id));
    }

    @GetMapping("log")
    public ResponseEntity<Object> log() throws IOException, InterruptedException {
        String url = "http://31.97.131.76:5678/api/v1/executions?workflowId=W9kHWzP0ZmxW4TfD&limit=20";

        HttpClient client = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())   // ✅ 使用系统/环境代理（与 Postman 一致）
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("X-N8N-API-KEY", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4Yjg1YzcwZC02ZWEwLTRiYzQtYWM4Yi01ZGYxNzM0YjQ3MzciLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzU1NjYzNTcyfQ.3NsQ1t_4QIx8iFnQVTEx0U_t_RupRijbrdycbP2gGNg")
                .GET()
                .build();

        try {
            HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.status(resp.statusCode()).body(resp.body());
        } catch (java.net.http.HttpTimeoutException e) {
            return ResponseEntity.status(504).body("n8n request timed out");
        }
    }



}

