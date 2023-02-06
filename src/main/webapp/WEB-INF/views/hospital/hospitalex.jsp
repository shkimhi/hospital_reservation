<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
<html>
<head>
    <title>병원</title>
</head>
<body>
<div class="container">
</div> <%--container--%>
<div class=" d-flex justify-content-center mt-2">

<input type="button" id="button" class="btn btn-dark btn-lg m-5"  value="병원 예약 서비스로 이동" onclick="location.href='/hospital/hospital/${name}'">
    <input type="button" id="logout" class="btn btn-dark btn-lg m-5"  value="로그아웃 / 로그인 창으로 이동" onclick="location.href='/member/logout'">

</div>
</body>
</html>
