<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Java后端WebSocket的Tomcat实现</title>
</head>

<body>
	Welcome
	<br />
	<input id="text" type="text" />
	<button onclick="send()">发送消息</button>
	<hr />
	<button onclick="closeWebSocket()">关闭WebSocket连接</button>
	<hr />
	<div id="message"></div>
</body>
<script type="text/javascript" charset="utf-8" src="js/pako.js"></script>
<script type="text/javascript">
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket(
				"ws://200.200.200.11:3124/RiverChiefSystem/userLocation/" + 6
						+ "/" + "B23FE13241A71732E9EE48607C5639C3");
	} else {
		alert('当前浏览器 Not support websocket')
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		setMessageInnerHTML("WebSocket连接发生错误");
	};

	//连接成功建立的回调方法
	websocket.onopen = function() {
		setMessageInnerHTML("WebSocket正在尝试连接服务器。");
	}

	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		  if(event.data == "连接成功，欢迎使用系统服务！"){
		       var i =  1;
			 	 function f(){
			 		websocket.send("{userId:"+i+", latitude:122222, longitude:1232.65}");
			 		i++;
				setTimeout(f, 1000); //循环调用触发setTimeout
				 }; 
				 setTimeout(f, 1000);//起始点
		}else if(event.data == "请上传正确的位置信息"){
			setMessageInnerHTML(event.data); 
		} 
		else{
			'use strict';
			var pako = window.pako;
			var strData= atob(event.data);//BASE64反解码
			var charData= strData.split('').map(function(x){return x.charCodeAt(0);});//转化为字符数阵
			var binData= new Uint8Array(charData);//字符数阵转为字节数组
			var data=pako.inflate(binData);
			var strData=String.fromCharCode.apply(null, new Uint16Array(data));
			setMessageInnerHTML(strData); 
		}
	}

	//连接关闭的回调方法
	websocket.onclose = function() {
		setMessageInnerHTML("WebSocket连接关闭");
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		closeWebSocket();
	}

	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('message').innerHTML += innerHTML + '<br/>';
	}

	//关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
	}

	//发送消息
	function send() {
		var message = document.getElementById('text').value;
		websocket.send(message);
	}
</script>
</html>