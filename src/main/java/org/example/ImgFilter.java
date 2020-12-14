package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;

public class ImgFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();
        if (httpServletRequest.getMethod().equals("POST")) {
            System.out.println("post");
            Part part = httpServletRequest.getPart("image");
            if (part != null) {
                String contentDisposition = part.getContentType();
                int end = contentDisposition.lastIndexOf("/");
                String contentType = contentDisposition.substring(0, end);
                if (!contentType.equals("image")) {
                   httpServletRequest.getServletContext().setAttribute("noImage", true);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
