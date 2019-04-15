<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>北大青鸟办公自动化管理系统</title>
    <link  href="css/login.css"  rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>

</head>

<body>
<div class="login-top"></div>
<div class="login-area">
    <form action="loginSysEmployeeAction" method="post">
        <table>
            <tr>
                <td><label>工&nbsp;&nbsp;号：</label></td>
                <td><s:textfield value="A1" name="sysEmployee.sn"/></td>
            </tr>
            <tr>
                <td><label>密&nbsp;&nbsp;码：</label></td>
                <td><s:password value="123" name="sysEmployee.password"/></td>
            </tr>
            <tr>
                <td colspan="2"><s:submit cssClass="login-sub" value="  " /></td>
            </tr>
        </table>
    </form>
</div>
<div class="login-copyright"></div>
</body>
</html>