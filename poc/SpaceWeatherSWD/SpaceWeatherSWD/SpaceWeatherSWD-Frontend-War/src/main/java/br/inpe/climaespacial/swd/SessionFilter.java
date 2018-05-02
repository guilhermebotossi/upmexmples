package br.inpe.climaespacial.swd;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SessionFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        if (!httpServletRequest.getHeader("accept").contains("text/html")
                || httpServletRequest.getRequestURI().contains("/resource/")) {
            chain.doFilter(req, res);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            try {

                requestDispatcher.forward(req, res);
            } catch (ServletException | IOException e) {
            }
        }
    }

    @Override
    public void destroy() {
    }
}
