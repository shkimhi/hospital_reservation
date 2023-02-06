<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

<html>
<head>
    <title>로그인/회원가입</title>
    <script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
    <style>
        h3{
            font-weight: bold;
        }

    </style>
</head>
<body>

<div class="container">
    <!--top.jsp  -->
    <!--  로그인 폼  -->
        <div class="col-md-12">
            <div class=" d-flex justify-content-center">
            <input type="button" id="login-button" class="btn btn-lg btn-dark text-white m-5 "  value="로그인" >
            <input type="button" id="signup-button" class="btn btn-lg btn-dark text-white m-5 "  value="회원가입" >
            </div>
        </div>


    <div class="asd">
        <form id="loginform" name="loginform" style="border: 1px solid black; border-radius: 10px; border-width: 3px;">
            <div class=" d-flex justify-content-center" >
            <h3 class="mt-3">로그인</h3>
            </div>
            <div class=" d-flex justify-content-center">
            <div class="mb-3" style="width: 500px;" >
                <label for="id" class="form-label">아이디</label>
                <input type="text" class="form-control" id="id" name="id" >
            </div>
            </div>
            <div class=" d-flex justify-content-center">
            <div class="mb-3" style="width: 500px;">
                <label for="pwd" class="form-label">비밀번호</label>
                <input type="password" class="form-control" id="pwd" name="pwd">
            </div>
            </div>
            <div class=" d-flex justify-content-center">
            <input type="submit" class="btn btn-dark mb-3" value="로그인">
            </div>
        </form>
</div>

    <div class="asd">
        <form id="signupform" class="validation-form needs-validation" novalidate method="post" style="border: 1px solid black; border-radius: 10px; border-width: 3px;" action="<c:url value='/member/insert'/>">
            <div class=" d-flex justify-content-center" >
                <h3 class="mt-3">회원가입</h3>
            </div>
            <div class=" d-flex justify-content-center">
                <div class="mb-3" style="width: 500px;" >
                    <label for="userName" class="form-label">이름</label>
                    <input type="text" class="form-control" id="userName" name="userName" required>
                    <div class="invalid-feedback">
                        이름을 입력해주세요
                    </div>
                </div>
            </div>

            <div class=" d-flex justify-content-center">
                <div class="mb-3" style="width: 500px;" >
                    <label for="userId" class="form-label">아이디</label>
                    <input type="text" class="form-control" id="userId"name="userId" required>
                    <div class="invalid-feedback">
                        아이디을 입력해주세요
                    </div>

                </div>
            </div>
            <div class=" d-flex justify-content-center">
                <div class="mb-3" style="width: 500px;">
                    <label for="userPw" class="form-label">비밀번호</label>
                    <input type="password" class="form-control" id="userPw" name="userPw"required>
                    <div class="invalid-feedback">
                        비밀번호을 입력해주세요
                    </div>
                </div>
            </div>
            <div class=" d-flex justify-content-center">
                <div class="mb-3" style="width: 500px;">
                    <label for="userPwchk" class="form-label">비밀번호확인</label>
                    <input type="password" class="form-control" id="userPwchk">
                    <div class="valid-feedback">
                        비밀번호가 같습니다!
                    </div>
                    <div class="invalid-feedback">
                        비밀번호가 틀립니다!
                    </div>

                </div>
            </div>
            <div id="errorMessage" style="color: red; display: none;"></div>
            <div class=" d-flex justify-content-center">
                <input type="submit" class="btn btn-dark mb-3" value="회원가입">
            </div>
        </form>
    </div>



</div>

<script>
    $(document).ready(function(){
        $('#loginform').on('submit', function(){
            //폼이 submit 되지 않도록 기본 기능 중단
            event.preventDefault();

            // 서버에 전송하고 결과 받아서 처리
            $.ajax({
                type:"post",
                url:"/member/login",
                data: {"id":$('#id').val(),
                    "pwd":$('#pwd').val()},
                dataType:'text',
                success:function(result){
                    if(result == "success"){
                        location.href="/hospital/hospitalex";
                    }else{
                        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
                    }

                },
                error:function(){
                    alert("실패");
                }
            }); // ajax 종료
        });// submit 종료
    });

</script>
<script>
    $(document).ready(function() {
        $("#loginform").show();
        $("#signupform").hide();

        $("#login-button").click(function() {
            $("#loginform").show();
            $("#signupform").hide();
        });

        $("#signup-button").click(function() {
            $("#signupform").show();
            $("#loginform").hide();
        });
    });
</script>
<script>

    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (() => {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        const forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    })()

</script>
<script>
    document.querySelector("#userPwchk").addEventListener("input",function (){
        // 입력한 value 값을 읽어온다.
        let inputPwchk = this.value;
        let userPw = $("#userPw").val();
        // 패스워드와 값이 같은 지 검사
        isPwchkValid = inputPwchk == userPw

        if(isPwchkValid == true){
            this.classList.remove("is-invalid");
            this.classList.add("is-valid");

        }else{
            this.classList.remove("is-valid");
            this.classList.add("is-invalid");
        }
    });
    document.querySelector("#signupform").addEventListener("submit", function(e){

        //만일 폼이 유효하지 않는다면 전송을 막아주기
        if(!isPwchkValid){
            //이벤트 객체의 함수를 이용해서 폼 전송 막아주기
            e.preventDefault();
            alert("비밀번호를 확인해주세요");
        }
    });

</script>

</body>
</html>
