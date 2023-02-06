# hospital_reservation
<h1>병원예약서비스</h1>

<h3>회원가입(ID / PW기반)</h3>
<h3>로그인</h3>

<h3>공공 데이터 포탈에서 병원데이터를 호출하여 병원리스트에 표현</h3>
API URL : https://www.data.go.kr/data/15001698/openapi.do<br>
병원명으로 검색<br>
페이징 처리(스크롤을 끝까지 내릴시 추가 검색)(무한스크롤)<br>

<h3>병원리스트 선택 시 상세정보화면에 병원 상세정보 표현</h3>
병원명, 주소, 전화번호, 진료과<br>

<h3>병원 예약</h3>
이름(회원가입시 입력한 사용자이름 표시 - 이름 가운데 * 로 변경 ex) 이*근, 황*)<br>
전화번호<br>
증상입력<br>
예약일시<br>
증상 이미지(file upload) / 업로드한 증상 이미지 표시<br>
날짜, 병원기준으로 중복 예약 방지<br>

<h3>예약리스트</h3>
예약일시 기준으로 오늘로부터 가장 빠른 일자순으로 정렬<br>

<h3>예약리스트 선택 시 상세정보화면에 예약 상세정보 표현</h3>
예약시 입력한 데이터 표시<br>
증상 이미지 표시<br>

<h3>예약수정</h3>
예약 상세정보에서 기 입력한 데이터 수정하여 예약수정 진행<br>

<h3>예약삭제</h3>
해당 예약 건 삭제<br>
