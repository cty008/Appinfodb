package alipayConfig;

import com.opensymphony.xwork2.ActionContext;
import pojo.SysEmployee;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter extends HttpServlet implements Filter {
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(true);

        String url = request.getRequestURI();
        SysEmployee admin  =(SysEmployee) ActionContext.getContext().getSession().get("user");
        if (admin == null || url.indexOf("login.jsp") == -1) {
            String location = "/login.jsp";
            request.getRequestDispatcher(location).forward(request, response);

            System.out.println("成功拦截到外星人企图入侵网站后台   :  " + url);
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
        } else {
            chain.doFilter(request, response);
        }
    }

}
