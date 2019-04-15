<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>北大青鸟办公自动化管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
	var hid='<tr class="mx"><td><select id="select" class="input_01"><option value="城际交通费">城际交通费</option><option value="市内交通费">市内交通费</option><option value="交际餐费">交际餐费</option><option value="礼品费">礼品费</option><option value="办公费">办公费</option><option value="住宿">住宿</option><option value="餐饮">餐饮</option><option value="其它">其它</option></select></td><td><input type="text" name=""  class="input_01" /></td><td><input type="text" name="" class="input_01" /></td><td><img class="write" src="images/edit.gif" width="16" height="16" /> <img class="del" src="images/delete.gif" width="16" height="16" /></td></tr>';
	//单击添加明细重新生成一行
	$("#add").click(function(){
		var b=true;  //用于判断返回的值是false还是true
		$("#main tr[class=mx]").each(function(){
			var $tr=$(this);
			//金额
			var $td2 = $tr.children("td:eq(1)");
			if($td2.find("input").val()==""||$td2.find("input").val()=='NaN'){
				alert('请填写金额！');
				b=false;
				return;
			}
		});
		if(b){  
			$("#test").before(hid);
		}
	});
	//单击删除一行明细时
	$(".del").live("click",function(){
		var count = $(this).parents("tr").find("td:eq(1)").html();
		var money=$("#money").html();
		$("#money").html(parseInt(money-count));
        $("#totalAccount").html(parseInt(money-count));
		$(this).parents("tr").remove();
	}); 
	$(".write").live("click",function(){
		var b=true;
		$(this).toggle(function(){
			var $tr=$(this).parents("tr");
			//项目
			var $td1 = $tr.children("td:eq(0)");
			//金额
			var $td2 = $tr.children("td:eq(1)");
			//费用
			var $td3 = $tr.children("td:eq(2)");
			if($td2.find("input").val()==""||$td2.find("input").val()=='NaN'){
				alert('请填写金额');
				b=false;
				return;
			}else if(!$.isNumeric($td2.find("input").val())){
				alert('请填写数值数据');
				b=false;
				return;
			}else{
				b=true;
			}
			if(b){
				$td1.html($td1.find("select").val());
				$td2.html($td2.find("input").val());
				$td3.html($td3.find("input").val());
				
				var count = $td2.html();
				var money=$("#money").html();
				if(money==''||money=='NaN'){
					money='0';
				}
				$("#money").html(parseInt(money)+parseInt(count));
                $("#totalAccount").html(parseInt(money)+parseInt(count));
			}
		},function(){
			if(b){
				var $tr=$(this).parents("tr");
				//项目
				var $td1 = $tr.children("td:eq(0)");
				var value1=$td1.html();
				$td1.html('<select name="" id="select" class="input_01"><option value="城际交通费">城际交通费</option><option value="市内交通费">市内交通费</option><option value="交际餐费">交际餐费</option><option value="礼品费">礼品费</option><option value="办公费">办公费</option><option value="住宿">住宿</option><option value="餐饮">餐饮</option><option value="其它">其它</option></select>');
				$td1.find("select").attr("value",value1);
				//金额
				var $td2 = $tr.children("td:eq(1)");
				var value2=$td2.html();
				$td2.html('<input type="text" name="" value="'+value2+'"  class="input_01" />');
				//费用
				var $td3 = $tr.children("td:eq(2)");
				var value3=$td3.html();
				$td3.html('<input type="text" name="" value="'+value3+'"  class="input_01" />');
				var money=$("#money").html();
				$("#money").html(parseInt(money)-parseInt(value2));
                $("#totalAccount").html(parseInt(money)-parseInt(value2));
			}
		});
	});
});
var json="";
function sub(num){
	if($("#jiner").val()=='0' || $("#jiner").val()==''||$("#jiner").val()=='NaN'
        ||$("#money").text()=='NaN'||$("#money").text()=='0'||$("#money").text()==''){
		alert('请填写金额再保存或者提交！');
		return;
	}
	json="[";
	var trs=$("#main tr[class=mx]");
	var i=1;
	var b=true;
	$("#main tr[class=mx]").each(function(){
		if($(this).find("td:eq(0)").html().length<=5){
			var mx="{";
			mx+="'item':'"+$(this).find("td:eq(0)").html()+"',";
			mx+="'account':'"+$(this).find("td:eq(1)").html()+"',";
			mx+="'des':'"+$(this).find("td:eq(2)").html()+"'";
			mx+="}";
			if(i<trs.length){
				mx+=",";
				i++;
			}
			json+=mx;
		}else if($(this).find("td:eq(1)").find("input").val()!="" || $(this).find("td:eq(2)").find("input").val()!=""){
			alert("请更改明细的状态为非文本框状态再提交！");
			b=false;
			return;
		}
	});
	json+="]";
	$("#json").val(json); //用来保存明细信息
	$("#hidd").val(num);//用来判断是保存还是提交
    $("#totalAccount").val($("#money").html()); //用来提交总金额totalAccount
	if(b){
		$("#form1").submit();
	}
}
</script>
</head>

<body>
<div class="top"><div class="global-width"><img src="images/logo.gif" class="logo" /></div></div>
<div class="status"><div class="global-width"><span class="usertype">【登录角色：<s:property value="#session.user.sysPositionByPositionId.nameCn"/>】</span><s:property value="#session.user.name"/>你好！欢迎访问青鸟办公管理系统！<span style="margin-left:50px;"><a href="${pageContext.request.contextPath }/emp_logout.action">退出</a></span></div></div>
<div class="main"><div class="global-width">
	
	<div class="nav" id="nav">
    	<div class="t"></div>
    	<dl class="open" style="height:240px;">
        	<dt onclick="this.parentNode.className=this.parentNode.className=='open'?'':'open';">报销单管理</dt>
            <s:if test="#session.user.sysPosition.id==1">
            	<dd><a href="${pageContext.request.contextPath }/claim_listBySelf.action" target="_self">查看报销单</a></dd>
            	<dd><a href="#" target="_self">添加报销单</a></dd>
            </s:if>
            <s:else>
            	<dd><a href="detail.jsp" target="_self">审核报销单</a></dd>
            </s:else>
        </dl>
    </div>
    <div class="action">
    	<div class="t">增加报销单</div>
   		<div class="pages">
        	<!--增加报销单 区域 开始-->
               <form id="form1" name="form1" method="post" action="claim_add">
                   <input type="hidden" name="hidd" id="hidd"/>
                   <input type="hidden" name="json" id="json"/>
                   <input type="hidden" name="claim.totalAccount" id="totalAccount"/><br/>

                  <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-base">
                  <caption>基本信息</caption>
                  <tr>
                    <td>填写人：<input name="claim.sysEmployeeByCreateSn.name" value="<s:property value="#session.user.name"/>" readonly="readonly" /></td>
                    <td>填报时间：<input name="claim.createTime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>" readonly="readonly"/></td>
<%--						<td>填报时间：<input name="claim.createTime" id="bgclock" readonly="readonly"/><span id="bgclock"></span></td>--%>
                 	<td>状态：新创建</td>
                  </tr>
                  <tr>
                  	<td>总金额：￥<span id="money">0</span></td>
                  </tr>
                </table>
                <p>&nbsp;</p>
                <table width="90%" border="0" cellspacing="0" cellpadding="0" class="addform-item">
                	<tbody id="main">
                  <tr>
                    <td>项目</td>
                    <td>金额</td>
                    <td>费用说明</td>
                    <td>操作</td>
                  </tr>
                  <tr class="mx">
                  	<td>
                        <select id="select" class="input_01">
                        	<option value="城际交通费">城际交通费</option>
                        	<option value="市内交通费">市内交通费</option>
                        	<option value="交际餐费">交际餐费</option>
                        	<option value="礼品费">礼品费</option>
                        	<option value="办公费">办公费</option>
                        	<option value="住宿">住宿</option>
                        	<option value="餐饮">餐饮</option>
                        	<option value="其它">其它</option>
                        </select>
                    </td>
                    <td><input type="text" id="jiner" class="input_01" value="1000" /></td>
                    <td><input type="text" class="input_01"  value="费用说明" /></td>
                    <td><img class="write" src="images/edit.gif" width="16" height="16" /> 
                    <img class="del" src="images/delete.gif" width="16" height="16" /></td>
                  </tr>
                  <!--报销单事由-->
                   <tr id="test"><td colspan="4" class="event">
                    <label>事  由：</label><textarea name="claim.event" id="textarea" cols="45" rows="5"></textarea>
                   </td></tr>    
                    
                  <!--表单提交行-->
                  <tr>
                    <td colspan="4" class="submit">
                    <input onclick="sub(1)" type="button" name="button" id="button" value="保 存" class="submit_01" />
                    <input onclick="sub(2)" type="button" name="button" id="button" value="提交" class="submit_01" />
                   	<input type="button" name="add" id="add" value="添加明细" class="submit_01" />
                   <input style="margin-top: 20px;" type="button" onclick="javascript:history.go(-1);" value="返回" class="submit_01" />
                    </td>
                   </tr> 
                  </tbody>
                </table>      
                </form>   
            <!--增加报销单 区域 结束-->
        </div>
    </div>
</div></div>
<div class="copyright">Copyright  &nbsp;   &copy;  &nbsp; 北大青鸟</div>
</body>
</html>