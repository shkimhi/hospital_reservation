package com.ex.lifesemantics.controller;

import com.fasterxml.jackson.core.JsonParser;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    //http://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?
    //hgg88bJKr1iTphtxUnhgXESsgycK28dsvnlrlgsBCzvPFfMJv3Qxg3Q0bvLAtWcuBbd%2BuJz0r7%2B%2BnNHbR4Fqrg%3D%3D
    private static final String SERVICE_KEY = "hgg88bJKr1iTphtxUnhgXESsgycK28dsvnlrlgsBCzvPFfMJv3Qxg3Q0bvLAtWcuBbd%2BuJz0r7%2B%2BnNHbR4Fqrg%3D%3D";

    @ResponseBody
    @RequestMapping(value="/api", produces="text/xml; charset=UTF-8")
    public String viewAirMethod(String location,int pageNo) throws IOException {
        String url = "http://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";
        url += "?servicekey=" + SERVICE_KEY; //서비스키 추가
        url += "&yadmNm=" + URLEncoder.encode(location,"UTF-8"); //지역명 추가(한글이 들어가면 인코딩처리필수)
        url += "&returnType=xml"; //리턴 타입
        url += "&numOfRows=50"; //결과 개수
        url += "&pageNo="+ pageNo; //페이지 수

//		작성된 url정보를 넣어 URL 객체 생성
        URL requestUrl = new URL(url);

        //생성된 URL 객체로 urlConnection 생성
        HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
        urlConnection.setRequestMethod("GET");

        //전달받은 응답데이터를 읽어줄 스트림 연결(기반스트림을 얻어와 보조스트림을 생성한다)
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String responseText = "";
        String line;

        //br로 읽어온 데이터를 line 변수에 넣고 더이상 읽을 데이터가 없을때까지 반복
        while((line = br.readLine()) != null) {
            responseText += line;
        }

        //자원반납 및 연결해제
        br.close();
        urlConnection.disconnect();

        return responseText;
    }
}


