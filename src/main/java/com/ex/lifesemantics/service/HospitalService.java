package com.ex.lifesemantics.service;

import com.ex.lifesemantics.dao.IHospitalDAO;
import com.ex.lifesemantics.model.HospitalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService implements IHospitalService{

    @Autowired
    @Qualifier("IHospitalDAO")
    IHospitalDAO dao;
    @Override
    public int Hospitalinsert(HospitalVO VO) {
        return dao.Hospitalinsert(VO);
    }

    @Override
    public List<HospitalVO> showreservation(String userName) {
        return dao.showreservation(userName);
    }

    @Override
    public HospitalVO detailreservation(int rNo) {
        return dao.detailreservation(rNo);
    }

    @Override
    public void updatehospital(HospitalVO VO) {
        dao.updatehospital(VO);

    }

    @Override
    public void deletehospital(int rNo) {
        dao.deletehospital(rNo);

    }
}
