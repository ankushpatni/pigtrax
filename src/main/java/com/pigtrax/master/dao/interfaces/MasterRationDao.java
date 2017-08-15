package com.pigtrax.master.dao.interfaces;

import java.util.List;

import com.pigtrax.master.beans.MasterRation;
import com.pigtrax.master.dto.MasterRationDto;

public interface MasterRationDao {
   Integer saveRation(MasterRation masterRationDto);
   Integer updateRation(MasterRation masterRationDto);
   Integer deleteRation(Integer id);
   List<MasterRationDto> getRationList();
   List<MasterRationDto> getRationList(String language,String activeUser);	
   MasterRationDto findRationById(Integer rationId);
}

