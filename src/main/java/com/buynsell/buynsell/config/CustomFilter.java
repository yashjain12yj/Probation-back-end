package com.buynsell.buynsell.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Configuration
public class CustomFilter implements Filter {

    private static final Logger log = Logger.getLogger(CustomFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("########## Initiating Custom filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("Logging Request  {" + request.getMethod() + "} : {" + request.getRequestURI() + "}");
        //call next filter in the filter chain
        filterChain.doFilter(request, response);
        log.info("Logging Response :{" + response.getContentType() + "}");
    }

    @Override
    public void destroy() {

    }
}
