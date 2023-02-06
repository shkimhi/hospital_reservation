package com.ex.lifesemantics.dao;

import com.ex.lifesemantics.model.HospitalVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IHospitalDAO {
    int Hospitalinsert(HospitalVO VO);
    List<HospitalVO> showreservation(String userName);
    public HospitalVO detailreservation(int rNo);
    public void updatehospital(HospitalVO VO);
    public void deletehospital(int rNo);
}
