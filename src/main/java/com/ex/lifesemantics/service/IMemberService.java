package com.ex.lifesemantics.service;

import com.ex.lifesemantics.model.MemberVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;


public interface IMemberService {
    public String loginCheck(HashMap<String, Object> map);
    public void insertMember(MemberVO vo);
    public String getName(String userId);



}
