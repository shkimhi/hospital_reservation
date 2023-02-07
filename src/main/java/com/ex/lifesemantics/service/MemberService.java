package com.ex.lifesemantics.service;

import com.ex.lifesemantics.dao.IMemberDAO;
import com.ex.lifesemantics.model.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class MemberService implements IMemberService {


@Autowired
@Qualifier("IMemberDAO")
IMemberDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String loginCheck(HashMap<String, Object> map) {
        String encodedPw = dao.loginCheck((String)map.get("id"));
        String result = "fail";
        // 암호화된 비밀번호와 입력해서 전달된 비밀번호와 일치하는지 확인
        if(encodedPw != null && passwordEncoder.matches((String)map.get("pwd"), encodedPw)) {
            result = "success";
        }
        // matches() : 평문과 암호화된 문장 비교
        return result;
    }

    @Override
    public void insertMember(MemberVO vo) {
        String encodedPassword = passwordEncoder.encode(vo.getUserPw());
        vo.setUserPw(encodedPassword);
        dao.insertMember(vo);

    }

    @Override
    public String getName(String userId) {
        return dao.getName(userId);
    }


}
