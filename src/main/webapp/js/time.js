
function disptime(){
	//获取当前时间
	var today=new Date();
	//获取小时、分钟、秒
	var hh=today.getHours();
	var mm=today.getMinutes();
	var ss=today.getSeconds();
	//获取年、月、日
	var year=today.getFullYear();
	var month=today.getMonth();
	var day=today.getDate();
	//获取星期
	var weekday=today.getDay();
	var xq="日";
	switch(weekday){
		case 1:
			xq="一";
			break;
		case 2:
			xq="二";
			break;
		case 3:
			xq="三";
			break;
		case 4:
			xq="四";
			break;
		case 5:
			xq="五";
			break;
		case 6:
			xq="六";
			break;
	}
	if(hh<=12&&hh>0){
		document.getElementById("myclock").innerHTML=
		year+"年"+(month+1)+"月"+day+"日&nbsp;&nbsp;"+hh+":"+mm+":"+ss+
		"&nbsp;&nbsp;AM&nbsp;&nbsp;星期"+xq+"&nbsp;&nbsp;";	
	}else{
		document.getElementById("myclock").innerHTML=
		year+"年"+(month+1)+"月"+day+"日&nbsp;&nbsp;"+hh+":"+mm+":"+ss+
		"&nbsp;&nbsp;PM&nbsp;&nbsp;星期"+xq+"&nbsp;&nbsp;";
	}
}
var myTime=setInterval("disptime()",1000);


