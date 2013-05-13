package com.distributedlife.mirror;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

public class MirrorService extends AbstractHandler {
    private String lastRequest = "";
    private Server server;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        lastRequest = new Scanner(request.getInputStream()).useDelimiter("\\Z").next();

        response.setContentType("text/xml;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().print(lastRequest);
    }

    public void startServer() throws Exception {
        server = new Server(8080);
        server.setHandler(this);
        server.start();
    }

    public void stopServer() throws Exception {
        server.stop();
    }

    public static void main(final String[] args) throws Exception {
        MirrorService system = new MirrorService();
        system.startServer();
        system.server.join();
    }
}