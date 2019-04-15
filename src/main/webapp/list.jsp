<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>北大青鸟办公自动化管理系统</title>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/lhgcore.js"></script>
<script type="text/javascript" src="js/lhgcalendar.js"></script>
<link rel="stylesheet" type="text/css"  href="css/style.css"/>
<script type="text/javascript">
	function sub(num){
		if(num>=-1 && num<=1){
            $("#pageIndex").val(parseInt("${page.pageIndex}")+parseInt(num));
		}else{
			$("#pageIndex").val(num);
		}
		$("form").submit();//表单提交
	}

</script>
</head>

<body>
<div class="top"><div class="global-width"><img src="images/logo.gif" class="logo" /></div></div>
<div class="status"><div class="global-width">
<span class="usertype">【登录角色：<s:property value="#session.user.sysPositionByPositionId.nameCn"/>】</span>
<s:property value="#session.user.name"/>你好！欢迎访问青鸟办公管理系统！<span style="margin-left:50px;">
<a href="emp_logout">退出</a></span></div></div>
<div class="main"><div class="global-width">
	
	<div class="nav" id="nav">
    	<div class="t"></div>
    	<dl class="open" style="height:240px;">
        	<dt onclick="this.parentNode.className=this.parentNode.className=='open'?'':'open';">报销单管理</dt>
            <s:if test="#session.user.sysPositionByPositionId.id==1">  <!-- 1代表是员工 -->
            	<dd><a href="#" target="_self">查看报销单</a></dd>
            	<dd><a href="add.jsp" target="_self">添加报销单</a></dd>
            </s:if>
            <s:else>
            	<dd><a href="#detail.jsp" target="_self">审核报销单</a></dd>
            </s:else>
        </dl>
    </div>
    <div class="action">
    	<div class="t">报销单列表</div>
   		<div class="pages">
        	<!--增加报销单 区域 开始-->
        	<form action="claim_listBySelf" method="post">
                <table width="95%" border="0" cellspacing="0" cellpadding="0" class="list">
                	<tr>
                		<td colspan="8">
                			报销单状态
                			<s:select  name="hiredate.status" list="#{'':'全部','新创建':'新创建','已提交':'已提交','待付款':'待付款','已打回':'已打回','已拒绝':'已拒绝','已付款':'已付款' }"></s:select>
							开始时间
							<s:textfield onfocus="WdatePicker({lang:'en'})" class="Wdate" id="startDate" name="hiredate.startDate"/>
							结束时间
							<s:textfield onfocus="WdatePicker({lang:'en'})" class="Wdate" id="endDate" name="hiredate.endDate"/>
                			<input type="submit" value="查询"/>
                		</td>
                	</tr>
                	<tr>
	                    <td>编号</td>
	                    <td>填报日期</td>
	                    <td>填报人</td>
	                    <td>总金额</td>
	                    <td>状态</td>
	                    <td>待处理人</td>
	                    <td>操作</td>
                  	</tr>
                	<s:iterator value="claims" >
                		<tr>
		                    <td><s:property value="id"/></td>
		                    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
		                    <td><s:property value="sysEmployeeByCreateSn.name"/></td>
		                    <td>￥<s:property value="totalAccount"/></td>
		                    <td><s:property value="status"/></td>
		                    <td><s:property value="sysEmployeeByNextDealSn.name"/></td>
		                    <s:if test="#session.user.sysPositionByPositionId.id==1">
			                    <td>
			                      <img onclick="location.href='claim_find?id=<s:property value="id"/>'" src="images/search.gif" width="16" height="15" title="查看" />
			              		  <s:if test="status=='新创建' || status=='已打回'">
			                    	<img onclick="location.href='claim_update?id=<s:property value="id"/>'" src="images/edit.gif" width="16" height="16" title="修改" />
			                      	<img onclick="location.href='claim_sub?id=<s:property value="id"/>'" src="images/sub.gif" width="16" height="16" title="提交" />
			                      </s:if>
			                      <s:if test="status=='新创建'">
			                      	<img onclick="location.href='claim_delete?id=<s:property value="id"/>'" src="images/delete.gif" width="16" height="16" title="删除" />
			                      </s:if>
			                    </td>
		                    </s:if>
		                    <s:else>
			                    <td>
			                      <img onclick="location.href='claim_find.action?id=<s:property value="id"/>'" src="images/search.gif" width="16" height="15" title="查看" />

								  <s:if test="#session.user.sysPositionByPositionId.nameCn=='部门经理' && status=='已提交'">
			                      	<img onclick="location.href='claim_detail.action?id=<s:property value="id"/>'" src="images/sub.gif" width="16" height="16" title="提交" />
			                      </s:if>
			                      <s:if test="#session.user.sysPositionByPositionId.nameCn=='总经理' && status=='待审批'">
			                      	<img onclick="location.href='claim_detail.action?id=<s:property value="id"/>'" src="images/sub.gif" width="16" height="16" title="提交" />
			                      </s:if>
			                      <s:if test="#session.user.sysPositionByPositionId.nameCn=='财务' && status=='待付款'">
			                      	<img onclick="location.href='claim_detail.action?id=<s:property value="id"/>'" src="images/sub.gif" width="16" height="16" title="提交" />
			                      </s:if>
			                    </td>
		                    </s:else>
		                </tr>
                	</s:iterator>
                  <tr>
                    <td colspan="6" align="center">
                     <input type="hidden" name="page.pageIndex" id="pageIndex"/>
					 <A onclick="sub()" href="#" >首页</A>
					 <A onclick="sub(-1)" href="#" >上一页</A>
					 <A onclick="sub(+1)" href="#" >下一页</A>
					 <A onclick="sub(${page.pageAll})" href="#" >末页</A>
  　<SPAN class=total><s:property value="page.pageIndex"/>/<s:property value="page.pageAll"/>页</SPAN>
    <span>　　共<s:property value="page.count"/>条记录</span>
			  		</td>
                  </tr>
                </table>        
            <!--增加报销单 区域 结束-->
            </form>
        </div>
    </div>
</div></div>

<div class="copyright">Copyright  &nbsp;   &copy;  &nbsp; 北大青鸟</div>

</body>
</html>