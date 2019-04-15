package action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import pojo.SysEmployee;
import service.SysEmployeeService;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class SysEmployeeAction extends ActionSupport {

    private SysEmployeeService sysEmployeeService;
    private SysEmployee sysEmployee;

    public String login1() throws Exception {
        SysEmployee user = (SysEmployee) ServletActionContext.getRequest().getSession().getAttribute("user");
        SysEmployee existUser = sysEmployeeService.loginService(user);
        // use the username and pwd check the user is exist
        if (existUser == null) {
            //login fail
            this.addActionError("error，your user or pwd is error, please login again...");
            return LOGIN;
        } else {
            // login success
            // set the user information into the session
            ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
            // goto the struts.xml ↓
            //<result name="loginSuccess" type="redirect">/admin/home.jsp</result>
            return "loginSuccess";
        }
    }

    //登陆
    public String login() throws Exception {

        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        sysEmployee = this.sysEmployeeService.loginService(this.sysEmployee);
        if (sysEmployee!=null){
            //将用户名存入session
            ActionContext.getContext().getSession().put("user",sysEmployee);
            if (sysEmployee.getStatus().toString().trim().equals("离职")){
                out.println("<script type='text/javascript'>"+"alert('登陆失败,该用户已离职');"+"location.href='login.jsp'; </script>");
                return Action.NONE;
            }else{
                return "loginSuccess";
            }
        }else {
            out.println("<script type='text/javascript'>"+"alert('用户名或密码错误,登陆失败');"+"location.href='login.jsp'; </script>");
            return Action.NONE;
        }
    }

    //退出
    public String logout()throws Exception{
        ActionContext.getContext().getSession().remove("user");
        return Action.LOGIN;
    }


    public SysEmployeeService getSysEmployeeService() {
        return sysEmployeeService;
    }
    public void setSysEmployeeService(SysEmployeeService sysEmployeeService) {
        this.sysEmployeeService = sysEmployeeService;
    }
    public SysEmployee getSysEmployee() {
        return sysEmployee;
    }
    public void setSysEmployee(SysEmployee sysEmployee) {
        this.sysEmployee = sysEmployee;
    }


}
