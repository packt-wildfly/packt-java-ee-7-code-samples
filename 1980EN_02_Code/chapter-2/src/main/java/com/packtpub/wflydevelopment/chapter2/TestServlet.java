package com.packtpub.wflydevelopment.chapter2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String MESSAGE = "<!DOCTYPE html><html>" + "<head><title>Hello!</title></head>"
        + "<body>Hello World WildFly</body>" + "</html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        try (PrintWriter out = response.getWriter()) {
            out.println(MESSAGE);
        }
    }
}
