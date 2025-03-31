package com.pages;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v121.network.Network;
import org.openqa.selenium.devtools.v121.network.Network.GetResponseBodyResponse;
import org.openqa.selenium.devtools.v121.network.model.Request;
import org.openqa.selenium.devtools.v121.network.model.RequestId;
import org.openqa.selenium.devtools.v121.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class NetworkInterceptorUtil {

    private DevTools devTools;
    private List<String> jsonRequests = new ArrayList<>();
    private List<String> jsonResponses = new ArrayList<>();
    private Map<RequestId, String> requestUrlMap = new HashMap<>();
    private AtomicReference<RequestId> requestIdRef = new AtomicReference<>();
    private BrowserMobProxy proxy;
    boolean isFirefoxProxyActive = false;

    public NetworkInterceptorUtil(WebDriver driver) {

        if (driver instanceof ChromeDriver) {
            this.devTools = ((ChromeDriver) driver).getDevTools();
            this.devTools.createSession();
            this.devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        } else if (driver instanceof EdgeDriver) {
            this.devTools = ((EdgeDriver) driver).getDevTools();
            this.devTools.createSession();
            this.devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        } else if (driver instanceof FirefoxDriver) {
            System.out.println("Setting up BrowserMob Proxy for Firefox.");
            proxy = new BrowserMobProxyServer();
            proxy.start(0);
            org.openqa.selenium.Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            System.out.println("Use these proxy settings when launching Firefox: " + seleniumProxy);
            proxy.newHar("firefoxTest");
            isFirefoxProxyActive = true;
        } else {
            throw new UnsupportedOperationException("Browser not supported for network interception.");
        }

    }

    public void startListening(String targetApi) {
        requestIdRef.set(null);
        jsonRequests.clear();
        jsonResponses.clear();

        // Capture API Requests
        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
                return;
            }

            if (req.getUrl().toLowerCase().contains(targetApi.toLowerCase())) {
                System.out.println("📌 API Request Sent: " + req.getUrl());
                System.out.println("📌 Request Method: " + req.getMethod());
                req.getPostData().ifPresent(data -> System.out.println("📌 Request Payload: " + data));

                requestUrlMap.put(request.getRequestId(), req.getUrl());

                // Capture request payload for POST, PUT, and DELETE
                if (req.getPostData().isPresent()) {
                    String requestPayload = req.getPostData().get();
                    jsonRequests.add(requestPayload);
                    System.out.println("📌 Request Payload: " + requestPayload);
                } else {
                    System.out.println("📌 No Request Payload");
                }

                requestUrlMap.put(request.getRequestId(), req.getUrl());
            }
        });

        // Capture API Responses
        devTools.addListener(Network.responseReceived(), response -> {
            Response res = response.getResponse();

            if (res.getUrl().toLowerCase().contains(targetApi.toLowerCase())) {
                System.out.println("📌 API Response Received: " + res.getUrl());
                System.out.println("📌 Status Code: " + res.getStatus());

                requestIdRef.set(response.getRequestId());

                if (res.getStatus() == 204) {
                    System.out.println("📌 No Response Body (204 No Content)");
                } else {
                    fetchResponseBody(response.getRequestId());
                }
            }
        });
    }

    private void fetchResponseBody(RequestId requestId) {
        if (requestId != null) {
            try {
                GetResponseBodyResponse responseBody = devTools.send(Network.getResponseBody(requestId));
                String responseText = responseBody.getBody();
                jsonResponses.add(responseText);

                // Fetch the correct request URL from the map
                String url = requestUrlMap.getOrDefault(requestId, "Unknown URL");
                System.out.println("📌 Response for " + url + ": " + responseText);
            } catch (Exception e) {
                System.out.println("⚠️ Error retrieving response body.");
            }
        }
    }


    public List<String> getAllJsonRequests() {
        return jsonRequests;
    }

    public List<String> getAllJsonResponses() {
        return jsonResponses;
    }

    public void waitForResponses(int maxWaitTimeInSeconds) throws InterruptedException {
        int elapsed = 0;
        while (jsonResponses.isEmpty() && elapsed < maxWaitTimeInSeconds) {
            Thread.sleep(500);
            elapsed += 1;
        }
    }
}
