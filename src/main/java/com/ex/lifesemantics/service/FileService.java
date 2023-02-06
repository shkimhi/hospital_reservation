package com.ex.lifesemantics.service;

import com.ex.lifesemantics.dao.IFileDAO;
import com.ex.lifesemantics.model.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements IFileService {
    @Autowired
    @Qualifier("IFileDAO")
    IFileDAO dao;

    @Override
    public List<FileVO> fileList(int rId) {
        return dao.fileList(rId);
    }

    @Override
    public int fileRegister(List<FileVO> fileList) {
        return dao.fileRegister(fileList);
    }

    @Override
    public FileVO fileDetail(int fileNo) {
        return dao.fileDetail(fileNo);
    }

    @Override
    public void fileDelete(List<FileVO> fileList) {
        dao.fileDelete(fileList);

    }

    @Override
    public List<String> imgFileList() {
        return dao.imgFileList();
    }

}
