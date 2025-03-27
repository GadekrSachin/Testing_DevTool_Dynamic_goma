package com.pages;
 
import java.util.Optional;
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
    private AtomicReference<RequestId> requestIdRef = new AtomicReference<>();
    private String latestJsonResponse;
    private String latestJsonRequest;  
    private BrowserMobProxy proxy;       
    boolean isFirefoxProxyActive = false;

    public NetworkInterceptorUtil(WebDriver driver) {
    	
    	if (driver instanceof ChromeDriver) {
             
            this.devTools = ((ChromeDriver) driver).getDevTools();
            this.devTools.createSession();
            this.devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        } else if (driver instanceof EdgeDriver) {
            // For Edge (EdgeDriver supports DevTools)
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
        latestJsonResponse = null;
        latestJsonRequest = null;
 
//        Payload
        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            if (req.getUrl().toLowerCase().contains(targetApi.toLowerCase())) {
                System.out.println("📌payload for API Request Sent: " + req.getUrl());
//                System.out.println("📌 Request Headers: " + req.getHeaders());
                System.out.println("📌payload Request Method: " + req.getMethod());
                if (req.getPostData().isPresent()) {
                    latestJsonRequest = req.getPostData().get();  
                    System.out.println("📌 Request Payload: " + latestJsonRequest);
                }
            }
        });

         devTools.addListener(Network.responseReceived(), response -> {
            Response res = response.getResponse();
            if (res.getUrl().toLowerCase().contains(targetApi.toLowerCase())) {
                System.out.println("📌 API Response Received: " + res.getUrl());
                System.out.println("📌 Status Code: " + res.getStatus());
//                System.out.println("📌 Response Headers: " + res.getHeaders());
                requestIdRef.set(response.getRequestId());   
            }
        });
    }

    public String getApiResponse() {
        if (requestIdRef.get() != null) {
            GetResponseBodyResponse responseBody = devTools.send(Network.getResponseBody(requestIdRef.get()));
            latestJsonResponse = responseBody.getBody();
            return latestJsonResponse;
        } else {
            System.out.println("❌ No response captured!");
            return null;
        }
    }

    public String getLatestJsonResponse() {
        return latestJsonResponse;
    }

//    	Payload
    public String getLatestJsonRequest() {
        return latestJsonRequest;
    }
    
//    if u want to call creatting object of classs

//	 String jsonRequest = networkUtil.getLatestJsonRequest();
//       String jsonResponse = networkUtil.getLatestJsonResponse();
    
//    if (jsonRequest != null) {
//        System.out.println("📌 Captured API Request Payload: " + jsonRequest);
//    } else {
//        System.out.println("❌ No API request payload captured!");
//    }
//
//    if (jsonResponse != null) {
//        System.out.println("📌 API JSON Response: " + jsonResponse);
//    } else {
//        System.out.println("❌ No API response captured!");
//    }
    
}
