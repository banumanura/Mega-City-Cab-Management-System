package com.megacitycab.filter;

import com.megacitycab.model.Employee;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee != null) {
                String role = employee.getRole();
                String requestURI = httpRequest.getRequestURI();

                // Restrict unauthorized access
                if (requestURI.contains("/admin") && !role.equals("ADMIN")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
                    return;
                } else if (requestURI.contains("/manager") && !role.equals("MANAGER")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
                    return;
                } else if (requestURI.contains("/receptionist") && !role.equals("RECEPTIONIST")) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
