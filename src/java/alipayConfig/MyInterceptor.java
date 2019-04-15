package alipayConfig;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;
import pojo.SysEmployee;

/**
 * 拦截登陆类
 */
public class MyInterceptor extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        SysEmployee employee  =(SysEmployee) ActionContext.getContext().getSession().get("user");

        if(employee!=null){
            // is logined ,have the userinfo in the session
            return actionInvocation.invoke();//递归调用拦截器
        }else{
            //is not logged
            ActionSupport support = (ActionSupport) actionInvocation.getAction();
            support.addActionError("请先登录:)");
            return "login";//返回到登录页面
        }
    }
}
