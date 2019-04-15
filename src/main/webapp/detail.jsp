<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>北大青鸟办公自动化管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function sub(num){
		if($("#textarea").val()==""){
			alert('请添加审批意见再提交！');
			return;
		}
		$("#hidd").val(parseInt(num));
		$("form").submit();
	}
</script>
</head>

<body>
<div class="top"><div class="global-width"><img src="images/logo.gif" class="logo" /></div></div>
<div class="status"><div class="global-width"><span class="usertype">
【登录角色：<s:property value="#session.user.sysPositionByPositionId.nameCn"/>】</span><s:property value="#session.user.name"/>你好！欢迎访问青鸟办公管理系统！<span style="margin-left:50px;">
<a href="emp_logout">退出</a></span></div></div>
<div class="main"><div class="global-width">
	
	<div class="nav" id="nav">
    	<div class="t"></div>
    	<dl class="open" style="height:240px;">
        	<dt onclick="this.parentNode.className=this.parentNode.className=='open'?'':'open';">报销单管理</dt>
            <dd><a href="query" target="_self">审核报销单</a></dd>
        </dl>
    </div>
    <div class="action">
    	<div class="t">审核报销单</div>
   		<div class="pages">
        		<!--基本信息-->
                <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-base" style="border-bottom: 1px solid gray;">
                  <caption>基本信息</caption>
                  <tr>
                  	<td>编号：<s:property value="claim.id"/></td>
                    <td>填写人：<s:property value="claim.sysEmployeeByCreateSn.name"/></td>
                   	<td>部门：<s:property value="claim.sysEmployeeByCreateSn.sysDepartmentByDepartmentId.name"/></td>
                   	<td>职位：<s:property value="claim.sysEmployeeByCreateSn.sysPositionByPositionId.nameCn"/></td>
                  </tr>
                  <tr>
                     <td>总金额：￥<s:property value="claim.totalAccount"/></td>
                    <td>填报时间：<s:date name="claim.createTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
                    <td>状态：<s:property value="claim.status"/></td>
                    <td>待处理人：<s:property value="claim.sysEmployeeByNextDealSn.name"/></td>
                  </tr>
                </table>
                <!-- 报销项目详情 -->
                <p>&nbsp;</p>
                <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-item" style="border-bottom: 1px solid gray;">
                  <tr>
                    <td>项目类别</td>
                    <td>项目金额</td>
                    <td>费用说明</td>
                  </tr>
                  <s:iterator value="claim.bizClaimVoucherDetailsById">
                  	<tr>
                  		<td><s:property value="item"/></td>
                  		<td><s:property value="account"/></td>
                  		<td><s:property value="des"/></td>
                  	</tr>
                  </s:iterator>
                   <tr><td colspan="3" class="event">
                    	事  由：<s:property value="claim.event" />
                   </td></tr>    
                </table>
                <!-- 审核情况 -->
                <s:if test="claim.bizCheckResultsById.size>0">
                <p>&nbsp;</p>
	                <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-item" style="border-bottom: 1px solid gray;">
	                  <s:iterator value="claim.bizCheckResultsById">
	                  	<tr>
	                  		<td>审 批 人：<s:property value="sysEmployeeByCheckerSn.name"/>(<s:property value="sysEmployeeByCheckerSn.sysPositionByPositionId.nameCn"/>)</td>
	                  		<%--<td>审批时间：<s:property value="checkTime"/></td>--%>
                            <td>审批时间： <s:date name="checkTime" format="yyyy-MM-dd HH:mm:ss" /></td>
	                  		<td>审核：
	                  			<s:if test="result=='通过'">
	                  				<span style="color:red"><s:property value="result"/></span>
	                  			</s:if>
	                  			<s:elseif test="result=='拒绝'">
	                  				<span style="color:purple"><s:property value="result"/></span>
	                  			</s:elseif>
	                  			<s:else>
	                  				<span style="color:blue"><s:property value="result"/></span>
	                  			</s:else>
	                  		</td>
	                  	</tr>
	                  	<tr>
	                  		<td colspan="3">审核意见：<span style="font-weight: bold;"><s:property value="comm"/></span></td>
	                  	</tr>
	                  </s:iterator>
	                </table>  
                </s:if>
                <s:if test="#session.user.sysPositionByPositionId.id==2"><!-- 部门经理审批处理 -->
                	<form action="claim_doDetail2.action" method="post">
                </s:if>
                <s:elseif test="#session.user.sysPositionByPositionId.id==3"><!-- 总经理审批处理 -->
                	<form action="claim_doDetail3.action" method="post">
                </s:elseif>
                <s:else>
                	<form action="claim_doDetail4.action" method="post"><!-- 财务审批处理 -->
                </s:else>
                 <p>&nbsp;</p>
                <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-item">
                  <tr>
                    <td>审批意见</td>
                  </tr>
                  <tr>
                    <td>
						<s:textarea id="textarea" name="comm" cssStyle="width:400px;height:80px;" value="同意"/>
						<input type="hidden" name="hidd" id="hidd"/>
						<input type="hidden" name="id" value="<s:property value='claim.id'/>"/>
					</td>
                  </tr>
                  <tr>
                  	<td>
                  	<input style="margin-top: 20px;" type="button" onclick="sub(1);" value="审批通过" class="submit_01" />
                  	<!-- 部门经理跟总经理都有拒绝跟打回的权力 -->
                  	<s:if test="#session.user.sysPositionByPositionId.id==2 || #session.user.sysPositionByPositionId.id==3">
	                	<input style="margin-top: 20px;" type="button" onclick="sub(2);" value="审批拒绝" class="submit_01" />
	                	<input style="margin-top: 20px;" type="button" onclick="sub(3);" value="打回" class="submit_01" />
                	</s:if>
                	<input style="margin-top: 20px;" type="button" onclick="javascript:history.go(-1);" value="返回" class="submit_01" />
                	</td>
                  </tr>
                </table>
                </form>
        </div>
    </div>
</div></div>
<div class="copyright">Copyright  &nbsp;   &copy;  &nbsp; 北大青鸟</div>
</body>
</html>