

// 时间戳格式化时间 例 1497254460000 格式化为 2017-06-12 16:06:00
function formatDate(timeStamp) {
	var time = new Date(timeStamp);
	var y = time.getFullYear();//年
	var m = time.getMonth() + 1;//月
	var d = time.getDate();//日
	var h = time.getHours();//时
	var mm = time.getMinutes();//分
	var s = time.getSeconds();//秒
	if(m < 10){
		m = '0' + m;
	}
	if(d < 10){
		d = '0' + d;
	}
	if(h < 10){
		h = '0'+ h;
	}
	if(mm < 10){
		mm = '0'+ mm;
	}
	if(s < 10){
		s = '0'+ s;
	}
	return y+"-"+m+"-"+d+" "+h+":"+mm+":"+s;
}


// 日期转换为时间戳  例2017/05/19 转为 1495123200000
//开始时间:getTimeStamp(start) --结束时间:getTimeStamp(end)+86400000(加一天)
//查询当天条件： >开始 and  <结束
function getTimeStamp(strTime){
	strTime = strTime.replace(/-/g,'/');
	var date = new Date(strTime);
	var timeStamp = date.getTime();
	return timeStamp;
}