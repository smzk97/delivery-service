package com.smzk.delivery_service.utils;


import java.net.http.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class HttpUtils {

    // 共享单例 HttpClient（线程安全，内部维护连接池）
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private HttpUtils() {}

    // ==========================================
    // 1. GET 请求
    // ==========================================

    public static String get(String url) throws IOException, InterruptedException {
        return get(url, Collections.emptyMap(), Collections.emptyMap());
    }

    public static String get(String url, Map<String, String> queryParams, Map<String, String> headers)
            throws IOException, InterruptedException {
        Map<String,String> headersMap = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        HttpRequest request = buildRequest(buildUrlWithParams(url, queryParams), headersMap)
                .GET()
                .build();
        return send(request);
    }

    // ==========================================
    // 2. POST 请求 (JSON / Form 表单)
    // ==========================================

    /**
     * 发送 POST JSON 请求
     */
    public static String postJson(String url, String jsonBody, Map<String, String> headers)
            throws IOException, InterruptedException {
        Map<String, String> headerMap = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        headerMap.put("Content-Type", "application/json; charset=UTF-8");

        HttpRequest request = buildRequest(url, headerMap)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody == null ? "" : jsonBody, StandardCharsets.UTF_8))
                .build();
        return send(request);
    }

    /**
     * 发送 POST 表单请求 (x-www-form-urlencoded)
     */
    public static String postForm(String url, Map<String, String> formData, Map<String, String> headers)
            throws IOException, InterruptedException {
        Map<String, String> headerMap = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        String formString = formatUrlEncodedParams(formData);
        HttpRequest request = buildRequest(url, headerMap)
                .POST(HttpRequest.BodyPublishers.ofString(formString, StandardCharsets.UTF_8))
                .build();
        return send(request);
    }

    // ==========================================
    // 3. PUT / DELETE 请求
    // ==========================================

    public static String putJson(String url, String jsonBody, Map<String, String> headers)
            throws IOException, InterruptedException {
        Map<String, String> headerMap = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        headerMap.put("Content-Type", "application/json; charset=UTF-8");

        HttpRequest request = buildRequest(url, headerMap)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody == null ? "" : jsonBody, StandardCharsets.UTF_8))
                .build();
        return send(request);
    }

    public static String delete(String url, Map<String, String> headers)
            throws IOException, InterruptedException {
        HashMap<String,String> headersMap  = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        HttpRequest request = buildRequest(url, headersMap)
                .DELETE()
                .build();
        return send(request);
    }

    // ==========================================
    // 4. 文件上传 / 下载
    // ==========================================

    /**
     * 上传二进制文件 (以 application/octet-stream 为例)
     */
    public static String uploadFile(String url, Path filePath, Map<String, String> headers)
            throws IOException, InterruptedException {
        HashMap<String,String> headersMap = new HashMap<>(headers != null ? headers : Collections.emptyMap());
        HttpRequest request = buildRequest(url, headersMap)
                .POST(HttpRequest.BodyPublishers.ofFile(filePath))
                .build();
        return send(request);
    }

    /**
     * 下载文件到本地指定路径
     */
    public static Path downloadFile(String url, Path targetPath) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<Path> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(targetPath));
        if (response.statusCode() >= 400) {
            throw new IOException("HTTP Download failed with status: " + response.statusCode());
        }
        return response.body();
    }

    // ==========================================
    // 5. 异步调用 (CompletableFuture)
    // ==========================================

    public static CompletableFuture<String> getAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .thenApply(HttpResponse::body);
    }

    // ==========================================
    // 内部私有辅助方法
    // ==========================================

    private static HttpRequest.Builder buildRequest(String url, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15));

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::header);
        }
        return builder;
    }

    private static String send(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        int code = response.statusCode();
        if (code >= 400) {
            throw new IOException("HTTP request failed with status: " + code + ", body: " + response.body());
        }
        return response.body();
    }

    private static String buildUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        String separator = url.contains("?") ? "&" : "?";
        return url + separator + formatUrlEncodedParams(params);
    }

    private static String formatUrlEncodedParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String val = URLEncoder.encode(entry.getValue() != null ? entry.getValue() : "", StandardCharsets.UTF_8);
            sj.add(key + "=" + val);
        }
        return sj.toString();
    }
}
