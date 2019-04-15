package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 	������--��½
 * @author admin
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("���ִ�У�����һ�������ͷ���Դ����");
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("Actionִ��֮��������ͼ֮ǰִ�У���");
//		response.sendRedirect("/appinfodb/403.jsp");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("action֮ǰִ�У�����");
		String url = request.getRequestURL().toString();
		System.out.println("url->"+url);
		
		int index = url.lastIndexOf('/');
		
		System.out.println("����ķ����ǣ�"+url.substring(index+1, url.length()));
		
		String action = url.substring(index+1, url.length());
		
		String str = (String) request.getSession().getAttribute("isLogin");  
        System.out.println("str=========>"+str);  
		
		if(action.equals("login")||str!=null||action.equals("dologin")){
			return true;  //����ִ��action,�����ص�½�ķ�����
		}
		response.sendRedirect("/appinfodb/403.jsp");
		return false;
	}
	
}
