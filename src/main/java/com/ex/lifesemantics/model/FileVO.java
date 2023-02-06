package com.ex.lifesemantics.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    private int fileNo; // 파일 ID
    private String originalFileName; // 파일의 원래 이름
    private String savedFileName; // 중복 방지를 위한 랜덤스트링 포함한 파일 이름
    private int rId; // 연결된 공간등록 글 번호

}
