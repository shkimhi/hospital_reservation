package com.ex.lifesemantics.service;

import com.ex.lifesemantics.model.HospitalVO;

import java.util.ArrayList;
import java.util.List;

public interface IHospitalService {
    int Hospitalinsert(HospitalVO VO);
    List<HospitalVO> showreservation(String userName);
    public HospitalVO detailreservation(int rNo);
    public void updatehospital(HospitalVO VO);
    public void deletehospital(int rNo);
}
