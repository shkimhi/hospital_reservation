package com.ex.lifesemantics.controller;

import com.ex.lifesemantics.model.FileVO;
import com.ex.lifesemantics.model.HospitalVO;
import com.ex.lifesemantics.service.FileService;
import com.ex.lifesemantics.service.HospitalService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HospitalController {

    @Autowired
    HospitalService service;
    @Autowired
    FileService fileService;
    @Value("${file.upload.path}")
    String uploadPath;
    @RequestMapping("/hospital/hospitalex")
    public String hospitalex(){

        return "/hospital/hospitalex";

    }

    @RequestMapping("/hospital/hospital/{userName}")
    public String Hospital(@PathVariable String userName, Model model,HttpSession httpSession){

        List<HospitalVO> HoList = service.showreservation(userName);
        model.addAttribute("HoList",HoList);
        return "hospital/hospital";
    }
    @RequestMapping("/hospital/hospital1/{rNo}")
    public String Hospital(@PathVariable int rNo, Model model,HospitalVO vo){
        HospitalVO ho = service.detailreservation(rNo);
        model.addAttribute("ho",ho);
        model.addAttribute("fileList",fileService.fileList(rNo));
        return "hospital/hospitalres";
    }
    @RequestMapping("/hospital/updatehospital/{rNo}")
    public String updatehospital(@PathVariable int rNo, HospitalVO VO,HttpSession httpSession){
        VO.setRNo(rNo);
        service.updatehospital(VO);

        String name = (String) httpSession.getAttribute("name");
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/hospital/hospital/"+ name;

    }

    @RequestMapping("/hospital/deletehospital/{rNo}")
    public String deletehospital(@PathVariable int rNo,HttpSession httpSession,HospitalVO VO) {
        if( VO.getRNo()>0) {
            service.deletehospital(rNo);// DB에 저장된 글 삭제

            // 서버에 저장된 실제 파일 삭제
            List<FileVO> fileList = fileService.fileList(rNo);
            for(int a=0; a<fileList.size(); a++) {
                File file = new File(System.getProperty("user.dir")+ uploadPath+ fileList.get(a).getSavedFileName());
                if(file.exists())
                    file.delete(); // 파일 유무 확인 후 삭제
            }
            if(fileList.size()>0)
                fileService.fileDelete(fileList); // 파일 정보 DB에서 삭제
        }
        String name = (String) httpSession.getAttribute("name");
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/hospital/hospital/"+ name;

    }



    @RequestMapping("/hospital/hospitalreservation")
    public String hospitalinsert(HospitalVO VO, HttpSession httpSession,MultipartFile[] files){

        VO.setUserName((String) httpSession.getAttribute("name"));
        service.Hospitalinsert(VO);


        //파일 저장소 위치 존재 확인 후 없으면 생성
        File folder = new File(System.getProperty("user.dir")+ uploadPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // 파일을 저장하고, 저장된 파일 목록을 생성
        List<FileVO> fileList = new ArrayList<FileVO>();
        for(int a=0; a<files.length; a++) {
            // 첨부 파일 유무 확인 후, 있으면 파일 저장
            if( files[a].isEmpty())
                continue;
            String originalFileName = files[a].getOriginalFilename(); //파일의 원래 이름
            String uploadedFileName = RandomStringUtils.randomAlphanumeric(10)+"_"+originalFileName; // 중복 방지를 위해 저장될 랜덤값 + 파일 이름
            File fileToUpload = new File( System.getProperty("user.dir")+ uploadPath+ uploadedFileName );
            try {
                files[a].transferTo(fileToUpload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 저장된 파일의 정보를 리스트로 보관
            FileVO fileInfo = new FileVO();
            fileInfo.setRId(VO.getRNo());
            fileInfo.setOriginalFileName(originalFileName);
            fileInfo.setSavedFileName(uploadedFileName);
            fileList.add(fileInfo);
        }

        // 저장된 파일 정보 리스트를 DB에 저장
        if(fileList.size()>0) {
            fileService.fileRegister(fileList);
        }
        String name = (String) httpSession.getAttribute("name");
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        System.out.println(httpSession.getAttribute("name"));
        return "redirect:/hospital/hospital/"+ name;
    }
    @RequestMapping(value="/files/{fileNo}/download", method= RequestMethod.GET)
    private String fileDownload(HttpServletRequest req, HttpServletResponse res, @PathVariable int fileNo, Model model) throws Exception {

        try{
            FileVO fileInfo = fileService.fileDetail(fileNo);

            String saveFolderPath = System.getProperty("user.dir")+ uploadPath; // 파일이 저장된 위치
            String savedFileName = fileInfo.getSavedFileName(); // 서버에 저장된 이름
            String originalFileName = fileInfo.getOriginalFileName(); // 실제 내보낼 파일 명

            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";

            //파일을 읽어 스트림에 담기
            try{
                file = new File(saveFolderPath, savedFileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = req.getHeader("User-Agent");

            //파일 다운로드 헤더 지정
            res.reset();
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    res.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    res.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    res.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(originalFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    res.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                res.setHeader("Content-Length", "" + file.length());
                os = res.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                res.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("ERROR : " + e.getMessage());
        }

        return "hospital/hospitalres";
    }



}
