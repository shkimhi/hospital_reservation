package com.ex.lifesemantics.dao;

import com.ex.lifesemantics.model.MemberVO;
import org.springframework.stereotype.Repository;

public interface IMemberDAO {

    public String loginCheck(String id);


    public void insertMember(MemberVO vo);

    public String getName(String userId);


}
