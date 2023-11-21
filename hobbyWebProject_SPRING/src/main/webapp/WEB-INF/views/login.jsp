<%@page import="java.math.BigInteger"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.security.SecureRandom"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/error/errorPage.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width = device-width , initial-scale = 1, user-scalable = no, maximum-scale = 1 , minimum-scale = 1">
<meta charset="UTF-8">
<title>Login</title>
<link rel="icon" href="image/logo.png">
<link rel="stylesheet" href="/resources/css/main.css?after">
<link rel="stylesheet" href="/resources/css/member.css?after">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/leaflet@1.6.0/dist/leaflet.css"/>
<link href="https://fonts.googleapis.com/css?family=Teko:300,400,500,600,700&display=swap" rel="stylesheet">
<link href="https://hangeul.pstatic.net/hangeul_static/css/nanum-square.css" rel="stylesheet">
<script src="option/jquery/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://kit.fontawesome.com/f95555e5d8.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script type="text/javascript" src="/resources/js/script.js"></script>

</head>
<style>
.login-wrapper{
	margin: 20px;
}
#error-text{
	color: red;
}
#login-bottom{
	width: 100%;
	display: flex;
	padding: 7px;
	margin: 0 auto;
}
#remember{
	width: 50%;
	float: left;
}
#join{
	width: 50%;
	padding: 5px;
	padding-left: 60px; 
	cursor: pointer;
	float: right;
}
</style>
<body>
<% 
String userID = null;
if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
}
%>
<header id="header">
<jsp:include page="/resources/header/header.jsp"/>
</header>
<section>
	<div class="login-wrapper">
	<div>
		<h2>로그인
		</h2>
		<form method="post" action="/login/action" role="form" id="login-form">
		    <input type="text" name="userID" id="userID" placeholder="아이디 입력">
		    <input type="password" name="userPassword" id="userPassword" placeholder="비밀번호 입력">
		    <c:if test="${loginError == 'fail'}">
		    	<span id="error-text">아이디 또는 비밀번호 오류입니다.</span>
		    </c:if>
		    <input type="submit" id="login" value="Login">
		</form>
		<div id="login-bottom">
			<div id="remember">
			    <label for="remember-check">
			        <input type="checkbox" id="remember-check">아이디 저장하기
			    </label>
		    </div>
			<div id="join" onclick="location.href='join'">회원가입 하러가기</div>
		</div>
	</div>
	</div>
</section>
<script>
$(document).ready(function(){
	// 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var key = getCookie("key");
    $("#userID").val(key); 
     
    // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
    if($("#userID").val() != ""){ 
        $("#remember-check").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
     
    $("#remember-check").change(function(){ // 체크박스에 변화가 있다면,
        if($("#remember-check").is(":checked")){ // ID 저장하기 체크했을 때,
            setCookie("key", $("#userID").val(), 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("key");
        }
    });
     
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("#userID").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#remember-check").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            setCookie("key", $("#userID").val(), 7); // 7일 동안 쿠키 보관
        }
    });
});
</script>
<script>
// 쿠키 저장하기 
// setCookie => saveid함수에서 넘겨준 시간이 현재시간과 비교해서 쿠키를 생성하고 지워주는 역할
function setCookie(cookieName, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var cookieValue = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue;
}

// 쿠키 삭제
function deleteCookie(cookieName) {
	var expireDate = new Date();
	expireDate.setDate(expireDate.getDate() - 1);
	document.cookie = cookieName + "= " + "; expires="
			+ expireDate.toGMTString();
}
 
// 쿠키 가져오기
function getCookie(cookieName) {
	cookieName = cookieName + '=';
	var cookieData = document.cookie;
	var start = cookieData.indexOf(cookieName);
	var cookieValue = '';
	if (start != -1) { // 쿠키가 존재하면
		start += cookieName.length;
		var end = cookieData.indexOf(';', start);
		if (end == -1) // 쿠키 값의 마지막 위치 인덱스 번호 설정 
			end = cookieData.length;
            console.log("end위치  : " + end);
		cookieValue = cookieData.substring(start, end);
	}
	return unescape(cookieValue);
}
</script>
</body>
</html>