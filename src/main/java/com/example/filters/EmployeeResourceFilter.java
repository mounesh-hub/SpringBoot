package com.example.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EmployeeResourceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        System.out.println("inside EmployeeResourceFilter, uri = "+request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
