package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 	拦截器--登陆
 * @author admin
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("最后执行！！！一般用于释放资源！！");
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("Action执行之后，生成视图之前执行！！");
//		response.sendRedirect("/appinfodb/403.jsp");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("action之前执行！！！");
		String url = request.getRequestURL().toString();
		System.out.println("url->"+url);
		
		int index = url.lastIndexOf('/');
		
		System.out.println("请求的方法是："+url.substring(index+1, url.length()));
		
		String action = url.substring(index+1, url.length());
		
		String str = (String) request.getSession().getAttribute("isLogin");  
        System.out.println("str=========>"+str);  
		
		if(action.equals("login")||str!=null||action.equals("dologin")){
			return true;  //继续执行action,不拦截登陆的方法。
		}
		response.sendRedirect("/appinfodb/403.jsp");
		return false;
	}
	
}
