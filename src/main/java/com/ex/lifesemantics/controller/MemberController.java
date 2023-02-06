package com.ex.lifesemantics.controller;

import com.ex.lifesemantics.model.MemberVO;
import com.ex.lifesemantics.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class MemberController {

    @Autowired
    MemberService service;

    @RequestMapping("/")
    public String loginform(){
        return "Member/loginform";
    }

    @RequestMapping("/member/insert")
    public String insert(MemberVO vo) {
        service.insertMember(vo);
        return "Member/loginform"; // 회원 가입 후 로그인 폼으로 이동
    }
    @RequestMapping("/member/logout")
    public String logout(HttpSession session) {
        //세션 무효화
        session.invalidate();
        return "Member/loginform";
    }


    @ResponseBody
    @RequestMapping("/member/login")
    public String loginCheck(@RequestParam HashMap<String, Object> param,
                             HttpSession session) {
        // 로그인 체크 결과
        String result = service.loginCheck(param); // result : "success" 또는 "fail"


        // 아이디와 비밀번호 일치하면 (로그인 성공하면)
        // 서비스에서 "success" 반환받았으면
        if(result.equals("success")) {
            //로그인 성공하면 세션 변수 지정
            String id = (String)param.get("id");
            session.setAttribute("sid", id);
            String name = service.getName(id);
            session.setAttribute("name",name);
        }

        return result;
    }
}
