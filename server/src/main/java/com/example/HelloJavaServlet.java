package com.example;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class HelloJavaServlet extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AtomicLong counter = new AtomicLong();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonData json = new JsonData();
        json.setHello("World!!" + counter.get());
        json.setPathInfo(request.getPathInfo());
        json.setQueryString(request.getQueryString());
        MAPPER.writeValue(response.getOutputStream(), json);
        counter.incrementAndGet();
    }
}