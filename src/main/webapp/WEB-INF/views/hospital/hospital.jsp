<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
<html>
<head>
    <title>병원 목록</title>
    <style>
        input:focus {outline:none;}
        .form-floating label {
            font-size: 13px;
        }
        #att_zone {
            width: 365px;
            min-height: 150px;
            padding: 10px;
            border: 1px solid #888888;
        }

        #att_zone:empty:before {
            content: attr(data-placeholder);
            color: #999;
            font-size: .9em;
        }
    </style>
</head>
<body>
<div class="container">

    <div class=" d-flex justify-content-center m-5">
        <h1>병원 예약 서비스</h1>
    </div>

    <div class="row">

        <div class="col-md-4" style="text-align: center;">
            <h4>병원 리스트</h4>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="병원 명 으로 검색해주세요" id="location"  aria-describedby="btn1">
                <button class="btn btn-outline-secondary" type="button" id="btn1">검색</button>
            </div>
            <div id = "hospitallist" style="border: 1px solid #ced4da; border-radius: 0.375rem; height: 715px;overflow:auto; border-top: none;">
            </div>
        </div><%--col-md-4 --%>

        <div class="col-md-4"style="text-align: center;" >
            <h4>예약 리스트</h4>
            <div id ="reservationlist" style="border: 1px solid #ced4da; border-radius: 0.375rem; height: 750px;overflow:auto;">
                <c:forEach var="HoList" items="${HoList}">
                    <div class='reservationlist m-3' id='hospital_${HoList.RNo}'  style='border-bottom: 1px solid black; cursor: pointer;'>
                        <input type='hidden' id="reList_${HoList.RNo}" value='${HoList.RNo}'>
                        <h5>${HoList.yadmNm}</h5>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-md-4" style="">
            <h4>상세 정보</h4>
            <div id="hospitalDetail_re"  style="border: 1px solid #ced4da; border-radius: 0.375rem; height: 750px; overflow:auto;">
            <form class="hospitalreservation m-3 "  method="post" action="<c:url value='/hospital/hospitalreservation'/>" enctype="multipart/form-data">
                <div id = "hospitaldetail" >
                </div>

                <div id = "member" style="display: none;">
                    <h3 style="font-weight: bold;" class="mt-2">진료신청</h3>
                    <div class="form-floating">
                        <input type="text" class="form-control mt-2" id="userName" name="userName" value="<%=session.getAttribute("name")%>" placeholder="이름" readonly >
                        <label for="userName">이름</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control mt-2" id="userHp" name="userHp" placeholder="전화번호" >
                        <label for="userHp">전화번호</label>
                    </div>
                    <div class="form-floating">
                        <textarea type="text" class="form-control mt-2" id="userSymptom" name="userSymptom" style="height: 100px;" placeholder="증상" ></textarea>
                        <label for="userSymptom">증상</label>
                    </div>
                    <div class="form-floating">
                        <input type="datetime-local" class="form-control mt-2" id="userDate" name="userDate" placeholder="예약일시" >
                        <label for="userDate">예약 일시</label>
                    </div>
                </div>

                <div id = "imageupload" style="display: none;">
                    <div id='image' class=" mt-3">
                        <div id='att_zone' data-placeholder='증상 사진을 첨부 하려면 파일 선택 버튼을 클릭하거나 파일을 드래그앤드롭 하세요'></div>
                        <input type='file' class="form-control" id='files' name="files" accept=".jpg, .png, .gif" multiple='multiple' />
                    </div>
                </div>

                <div class=" d-flex justify-content-center mt-2">
                    <div id ="submit" style="display: none;">
                        <input type="button" id="button" class="btn btn-dark btn-lg "  value="예약하기" >
                    </div>
                </div>

            </form>
            </div>
        </div>
    </div> <%--row--%>
    <div class=" d-flex justify-content-center mt-2">
    <input type="button" id="logout" class="btn btn-dark btn-lg m-5"  value="로그아웃(메인이동)" onclick="location.href='/member/logout'">
    </div>

</div> <%--container--%>

<script>
    $(document).ready(function(){
        let pageNo = 1;
        let isLoading = false;

        $("#hospitallist").scroll(function(){
            if($("#hospitallist").scrollTop() + $("#hospitallist").innerHeight() >= $("#hospitallist")[0].scrollHeight){
                if(!isLoading){
                    isLoading = true;
                    $("#btn1").click();
                }
            }
        });

        $("#btn1").click(function(){
            isLoading = true;
            $.ajax({
                url: "/api",
                data: {location:$("#location").val(), pageNo: pageNo},
                success: function(result){
                    var itemArr = $(result).find("item");
                    var value = "";
                    itemArr.each(function(index, item){
                        console.log(index);

                        value += "<div class='hospital-info m-3' id='hospital-" + index + "' style='border-bottom: 1px solid black; cursor: pointer;'>"
                            + "<h5>" + $(item).find("yadmNm").text() + "</h5>"
                            + "</div>";
                    })
                    $("#hospitallist").append(value);
                    pageNo++;
                    isLoading = false;

                    $(".hospital-info").click(function(){
                        $(".hospital-info").css("background-color", "initial");
                        $(".reservationlist").css("background-color", "initial");
                        $(this).css("background-color", "rgba(0,0,0,0.2)");
                        var id = $(this).attr("id").split("-")[1];
                        var hospital = itemArr[id];

                        var detail = "<h3 style='font-weight: bold;'>"+"병원정보"+"</h3>"
                            + "<div class='hospital-detail mt-3' style='border: 1px solid black; width: 365px; height:auto;'>"
                            + "<p>" +"<strong>" + "병원명: " + "</strong>" + "<input type='text' name='yadmNm' value='" + $(hospital).find("yadmNm").text() + "' style='border:none; width:320px;' readonly>"+ "</p>"
                            + "<p>" +"<strong>" + "주소: " + "</strong>" + "<input type='text' name='addr' value='" + $(hospital).find("addr").text() + "' style='border:none; width:320px;' readonly>" + "</p>"
                            + "<p>" +"<strong>" + "전화번호: " + "</strong>" + "<input type='text' name='telno' value='" + $(hospital).find("telno").text() + "' style='border:none; width:320px;' readonly>" + "</p>"
                            + "<p>" +"<strong>" + "진료과: " + "</strong>" + "<input type='text' name='clCdNm' value='" + $(hospital).find("clCdNm").text() + "' style='border:none; width:320px;' readonly>" + "</p>"
                            + "</div>"

                        $("#hospitaldetail").html(detail);

                        $("#member").show();
                        $("#imageupload").show();
                        $("#submit").show();
                        $("#member_reservation").hide();
                        $(".hospital-detail_reservation").hide();
                        $('#hospitaldetail').show();

                    })
                },
                error: function(){
                    console.log("통신실패");
                    alert("통신 실패 다시 검색해주세요.");
                }
            })
        })
    })
    $("#button").click(function(){
        var answer = confirm("예약 하시겠습니까?");
        if(answer){
            $("form.hospitalreservation").submit();
        }
    });

</script>
<script>
    var elements = document.querySelectorAll('.reservationlist');
    elements.forEach(function(element) {
        var userRNo = document.getElementById('reList_' + element.id.split('_')[1]).value;
        console.log(userRNo);
        $("#hospital_" + userRNo).click(function() {
            $.ajax({
                type: "post",
                url: "/hospital/hospital1/" + userRNo,
                data: userRNo,
                success: function(result) {
                    $(document).ajaxComplete(function(){
                        let userName = document.querySelector("#userName_re");
                        let name = userName.value;
                        if (name.length === 2) {
                            userName.value = name[0] + "*";
                        } else if (name.length > 2) {
                            let maskedName = "";
                            for (let i = 0; i < name.length - 2; i++) {
                                maskedName += "*";
                            }
                            userName.value = name[0] + maskedName + name[name.length - 1];
                        }
                    });
                    $('#hospitaldetail').html(result);
                },
                error: function() {
                    alert("실패");
                }
            }); // ajax 종료
        });
    });
</script>
<script>
    $(function(){
        $(".reservationlist").click(function(){
            $(".hospital-info").css("background-color", "initial");
            $(".reservationlist").css("background-color", "initial");
            $(this).css("background-color", "rgba(0,0,0,0.2)");
            $("#member").hide();
            $("#imageupload").hide();
            $("#submit").hide();
            $(".hospital-detail").hide();
        })
    })
</script>

<script>
    ( /* att_zone : 이미지들이 들어갈 위치 id, btn : file tag id */
        imageView = function imageView(att_zone, btn){

            var attZone = document.getElementById(att_zone);
            var files = document.getElementById(btn)
            var sel_files = [];

            // 이미지와 체크 박스를 감싸고 있는 div 속성
            var div_style = 'display:inline-block;position:relative;'
                + 'width:150px;height:120px;margin:5px;border:1px solid #00f;z-index:1';
            // 미리보기 이미지 속성
            var img_style = 'width:100%;height:100%;z-index:none';
            // 이미지안에 표시되는 체크박스의 속성
            var chk_style = 'width:20px;height:20px;position:absolute;font-size:15px;'
                + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#f00';

            files.onchange = function(e){
                var files = e.target.files;
                var fileArr = Array.prototype.slice.call(files)
                for(f of fileArr){
                    imageLoader(f);
                }
            }
            // 탐색기에서 드래그앤 드롭 사용
            attZone.addEventListener('dragenter', function(e){
                e.preventDefault();
                e.stopPropagation();
            }, false)

            attZone.addEventListener('dragover', function(e){
                e.preventDefault();
                e.stopPropagation();

            }, false)

            attZone.addEventListener('drop', function(e){
                var files = {};
                e.preventDefault();
                e.stopPropagation();
                var dt = e.dataTransfer;
                files = dt.files;
                for(f of files){
                    imageLoader(f);
                }
            }, false)

            /*첨부된 이미리즐을 배열에 넣고 미리보기 */
            imageLoader = function(file){
                sel_files.push(file);
                var reader = new FileReader();
                reader.onload = function(ee){
                    let img = document.createElement('img')
                    img.setAttribute('style', img_style)
                    img.src = ee.target.result;
                    attZone.appendChild(makeDiv(img, file));
                }

                reader.readAsDataURL(file);
            }

            /*첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
            makeDiv = function(img, file){
                var div = document.createElement('div')
                div.setAttribute('style', div_style)

                var btn = document.createElement('input')
                btn.setAttribute('type', 'button')
                btn.setAttribute('value', 'x')
                btn.setAttribute('delFile', file.name);
                btn.setAttribute('style', chk_style);
                btn.onclick = function(ev){
                    var ele = ev.srcElement;
                    var delFile = ele.getAttribute('delFile');
                    for(var i=0 ;i<sel_files.length; i++){
                        if(delFile== sel_files[i].name){
                            sel_files.splice(i, 1);
                        }
                    }

                    dt = new DataTransfer();
                    for(f in sel_files) {
                        var file = sel_files[f];
                        dt.items.add(file);
                    }
                    files.files = dt.files;
                    var p = ele.parentNode;
                    attZone.removeChild(p)
                }
                div.appendChild(img)
                div.appendChild(btn)
                return div
            }
        }
    )('att_zone', 'files')

</script>
<script>
    const name = "<%=session.getAttribute("name")%>";
    const nameLength = name.length;
    let maskedName = '';

    if (nameLength === 2) {
        maskedName = name[0] + '*';
    } else if (nameLength > 2) {
        let maskedChars = '';
        for (let i = 0; i < nameLength - 2; i++) {
            maskedChars += '*';
        }
        maskedName = name[0] + maskedChars + name[nameLength - 1];
    } else {
        maskedName = name;
    }
    document.getElementById('userName').value = maskedName;

</script>

</body>
</html>
