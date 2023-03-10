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
            service.deletehospital(rNo);// DB??? ????????? ??? ??????

            // ????????? ????????? ?????? ?????? ??????
            List<FileVO> fileList = fileService.fileList(rNo);
            for(int a=0; a<fileList.size(); a++) {
                File file = new File(System.getProperty("user.dir")+ uploadPath+ fileList.get(a).getSavedFileName());
                if(file.exists())
                    file.delete(); // ?????? ?????? ?????? ??? ??????
            }
            if(fileList.size()>0)
                fileService.fileDelete(fileList); // ?????? ?????? DB?????? ??????
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


        //?????? ????????? ?????? ?????? ?????? ??? ????????? ??????
        File folder = new File(System.getProperty("user.dir")+ uploadPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // ????????? ????????????, ????????? ?????? ????????? ??????
        List<FileVO> fileList = new ArrayList<FileVO>();
        for(int a=0; a<files.length; a++) {
            // ?????? ?????? ?????? ?????? ???, ????????? ?????? ??????
            if( files[a].isEmpty())
                continue;
            String originalFileName = files[a].getOriginalFilename(); //????????? ?????? ??????
            String uploadedFileName = RandomStringUtils.randomAlphanumeric(10)+"_"+originalFileName; // ?????? ????????? ?????? ????????? ????????? + ?????? ??????
            File fileToUpload = new File( System.getProperty("user.dir")+ uploadPath+ uploadedFileName );
            try {
                files[a].transferTo(fileToUpload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // ????????? ????????? ????????? ???????????? ??????
            FileVO fileInfo = new FileVO();
            fileInfo.setRId(VO.getRNo());
            fileInfo.setOriginalFileName(originalFileName);
            fileInfo.setSavedFileName(uploadedFileName);
            fileList.add(fileInfo);
        }

        // ????????? ?????? ?????? ???????????? DB??? ??????
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

            String saveFolderPath = System.getProperty("user.dir")+ uploadPath; // ????????? ????????? ??????
            String savedFileName = fileInfo.getSavedFileName(); // ????????? ????????? ??????
            String originalFileName = fileInfo.getOriginalFileName(); // ?????? ????????? ?????? ???

            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";

            //????????? ?????? ???????????? ??????
            try{
                file = new File(saveFolderPath, savedFileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = req.getHeader("User-Agent");

            //?????? ???????????? ?????? ??????
            res.reset();
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    res.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 ??????.
                } else if (client.indexOf("Trident") != -1) {
                    res.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // ?????? ????????? ??????
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
                System.out.println("<script language='javascript'>alert('????????? ?????? ??? ????????????');history.back();</script>");
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
