package com.ex.lifesemantics.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HospitalVO {

    private int rNo;
    private String clCdNm; // 진료과
    private String telno; // 전화번호
    private String addr; // 주소
    private String yadmNm; // 병원명
    private String userName; //이름
    private String userHp; // 번호
    private String userSymptom; // 증상
    private String  userDate; // 날짜
}
