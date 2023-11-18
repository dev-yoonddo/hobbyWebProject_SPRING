<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width = device-width , initial-scale = 1, user-scalable = no, maximum-scale = 1 , minimum-scale = 1">
<meta charset="UTF-8">
<title>TOGETHER</title>
<link rel="icon" href="/resources/image/logo.png">
<link rel="stylesheet" href="/resources/css/main.css?after">
<link rel="stylesheet" href="/resources/css/member.css?after">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/leaflet@1.6.0/dist/leaflet.css"/>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=IBM+Plex+Sans+KR:wght@300;600&family=Jua&family=Merriweather:wght@700&family=Nanum+Gothic&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@500&family=Noto+Serif+KR:wght@200&display=swap" rel="stylesheet">
<link href="https://hangeul.pstatic.net/hangeul_static/css/nanum-square.css" rel="stylesheet">
<script defer src="option/jquery/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="resources/js/script.js"></script>
<script src="https://kit.fontawesome.com/f95555e5d8.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

</head>
<style>
::-webkit-scrollbar {
	display: none;
}

section {
	width: 100%;
	height: 500px;
	display: flex;
}

.select {
	position: relative;
	width: 500px;
}

.select .option-list {
	position: absolute;
	top: 100%;
	left: 0;
	width: 100%;
	overflow: hidden;
	max-height: 0;
}

.select.active .option-list {
	max-height: none;
}

/* select css */
#select-sec {
	width: 550px;
	height: 55px;
	font-size: 16pt;
	font-weight: bold;
	color: #6e6e6e;
	font-family: 'Nanum Gothic', sans-serif;
	display: flex;
}

#select-sec .select {
	box-shadow: 0 0 10px #86A5FF;
	border-radius: 15px;
	padding: 15px;
	cursor: pointer;
}

#select-sec .select .text {
	font-size: 16pt;
	font-weight: bold;
	color: #6e6e6e;
	display: flex;
}

.option {
	display: flex;
}

span {
	margin: 0 auto;
}

#select-sec .select .option-list {
	list-style: none;
	padding: 0;
	border-radius: 15px;
	box-shadow: 0 0 10px #86A5FF;
}

#select-sec .select .option-list .option {
	padding: 15px;
}

#select-sec .select .option-list .option:hover {
	border-radius: 15px;
	background-color: #E0EBFF;
}

button {
	border: 0;
	outline: 0;
	width: 70px;
	height: 55px;
	border-radius: 15px;
	border-color: #86A5FF;
	background-color: #E0EBFF;
	margin-left: 15px;
	box-shadow: 0 0 10px #86A5FF;
	cursor: pointer;
}

#sc {
	font-size: 16pt;
	font-weight: bold;
	color: 6e6e6e;
	font-family: 'Nanum Gothic', sans-serif;
}

@media screen and (max-width:650px) {
	#select-sec {
		width: 400px;
	}
	#select-sec .select .text {
		font-size: 13pt;
	}
}
</style>
<body>
<%
	String userID=null;
	if(session.getAttribute("userID")!=null){
		userID=(String)session.getAttribute("userID");
	}
%>
<header>
	<jsp:include page="/resources/header/header.jsp"/>
</header>
<section>
<div class="select-hobby">
<form method="post" id ="searchField2" action="javascript:search()">
	<div id="select-sec">
		<div class="select">
			<div class="text">
				<input type="hidden" name="category">
				<span>함께 하고싶은 취미를 선택하세요 !</span>
				<span><i class="fa-solid fa-chevron-down"></i></span>
			</div>
		<ul class="option-list">
			<li class="option"><input type="hidden" name="category" id="SPORTS" value="SPORTS"><span>SPORTS</span></li>
			<li class="option"><input type="hidden" name="category" id="LEISURE" value="LEISURE"><span>LEISURE</span></li>
			<li class="option"><input type="hidden" name="category" id="MUSIC" value="MUSIC"><span>MUSIC</span></li>
			<li class="option"><input type="hidden" name="category" id="OTHER" value="OTHER"><span>OTHER</span></li>
		</ul>
		</div>
		<div id="submit-btn">
			<button type="submit"><span id="sc">검색</span></button>
		</div>
	</div>
</form>
</div>
</section>


<script>
function 
//select box 클릭하면 접고 펼치기
function onClickSelect(e) {
	const isActive = e.currentTarget.className.indexOf("active") !== -1;
	if (isActive) {
	  e.currentTarget.className = "select";
	} else {
	  e.currentTarget.className = "select active";
	}
}
document.querySelector("#select-sec .select").addEventListener("click", onClickSelect);

//클릭한 값을 박스안에 넣기
function onClickOption(e) {
	const selectedValue = e.currentTarget.innerHTML;
	document.querySelector("#select-sec .text").innerHTML = selectedValue;
}

var optionList = document.querySelectorAll("#select-sec .option");
for (var i = 0; i < optionList.length; i++) {
	var option = optionList[i];
	option.addEventListener("click", onClickOption);
}
  
//검색버튼 깜빡이기
var speed=500 

function flashit(){ 
	var flash=document.getElementById? document.getElementById("sc") : document.all? document.all.myexample : "" 
	if (flash){ 
		if (flash.style.color.indexOf("rgb(255, 255, 255)")!=-1) 
			flash.style.color="#6e6e6e" 
		else 
			flash.style.color="rgb(255, 255, 255)" 
	}
} 
setInterval("flashit()", speed);
</script>

</body>
</html>