<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
<html>
<head>
    <title>병원 목록</title>
</head>
<body>
<div class="container">
        <div id = "hospitaldetail" >
            <form class="updatehospital" method="post" action="<c:url value='/hospital/updatehospital/${ho.RNo}'/>" enctype="multipart/form-data">
                <h3 style="font-weight: bold;">병원정보</h3>
                <div class='hospital-detail_reservation mt-3' id ="detail_reservation_${ho.userDate}" style='border: 1px solid black; width: 365px; height:auto;'>
                    <p><strong>병원명: </strong> <input type='text' name='yadmNm' value='${ho.yadmNm}' style='border:none; width:320px;' readonly></p>
                    <p><strong>주소: </strong> <input type='text' name='addr' value='${ho.addr}' style='border:none; width:320px;' readonly></p>
                    <p><strong>전화번호: </strong><input type='text' name='telno' value='${ho.telno}' style='border:none; width:320px;' readonly></p>
                    <p><strong>진료과: </strong><input type='text' name='clCdNm' value='${ho.clCdNm}' style='border:none; width:320px;' readonly></p>
                    <input type='hidden' id="reDate_${ho.userDate}" value='${ho.userDate}'>
                </div>
                <div id = "member" >
                    <h3 style="font-weight: bold;" class="mt-2">진료신청</h3>
                    <div class="form-floating">
                        <input type="text" class="form-control mt-2" id="userName_re" name="userName" value="${ho.userName}"  placeholder="이름" readonly>
                        <label for="userName_re">이름</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control mt-2 onlyNumber" id="userHp_re" name="userHp" value="${ho.userHp}"  placeholder="전화번호" maxlength='11'>
                        <label for="userHp_re">전화번호</label>
                    </div>
                    <div class="form-floating">
                        <textarea type="text" class="form-control mt-2" id="userSymptom_re" name="userSymptom" value=""  style="height: 100px;" placeholder="증상">${ho.userSymptom}</textarea>
                        <label for="userSymptom_re">증상</label>
                    </div>
                    <div class="form-floating">
                        <input type="date" class="form-control mt-2" id="userDate_re" name="userDate" value="${ho.userDate}"  placeholder="예약일시" onchange="setMinValue()">
                        <label for="userDate_re">예약 일시</label>
                    </div>
                </div>
            <div id='image' class="mt-3">
                <c:forEach var="file" items="${fileList}">
                    <img src="/files/${file.fileNo}/download" style="width:150px; height:150px;"/>
                </c:forEach>
            </div>
            <div class=" d-flex justify-content-center mt-2">
                <div id ="submit" >
                    <input type="button" id="update" class="btn btn-dark btn-lg "  value="수정" >
                    <input type="button" id="delete" class="btn btn-dark btn-lg " value="삭제" >
                </div>
            </div>
            </form>
        </div>

</div><%--container--%>
<script>
    $("#delete").click(function(){
                var answer = confirm("삭제 하시겠습니까?");
        if(answer){
            location.href="/hospital/deletehospital/${ho.RNo}";
        }
        });
        $("#update").click(function(){
            var answer = confirm("수정 하시겠습니까?");
            if(answer){
                $("form.updatehospital").submit();
            }
        });
</script>


</body>
</html>
