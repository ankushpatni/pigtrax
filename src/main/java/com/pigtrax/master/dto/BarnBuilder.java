package com.pigtrax.master.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.master.dto.Barn;
import com.pigtrax.pigevents.dto.BarnDto;

@Component
public class BarnBuilder {
	
	/**
	 * Covert Barn object to Dto
	 * @param barn
	 * @return
	 */
       public BarnDto convertToDto(Barn barn)
       {
    	   if(barn != null)
    	   {
    		   BarnDto dto = new BarnDto();
    		   dto.setId(barn.getId());
    		   dto.setBarnId(barn.getBarnId());
    		   dto.setActive(barn.isActive());
    		   dto.setArea(barn.getArea());
    		   dto.setFeederCount(barn.getFeederCount());
    		   dto.setLastUpdated(barn.getLastUpdated());
    		   dto.setUserUpdated(barn.getUserUpdated());
    		   dto.setLocation(barn.getLocation());
    		   dto.setPhaseTypeId(barn.getPhaseTypeId());
    		   dto.setPremiseId(barn.getPremiseId());
    		   dto.setVentilationTypeId(barn.getVentilationTypeId());
    		   dto.setWaterAccessCount(barn.getWaterAccessCount());
    		   dto.setYear(barn.getYear());
    		   return dto;
    	   }
    	   return null;
       }
       
       
       /**
        * Convert List of Barns to Dtos
        * @param barnList
        * @return
        */
       public List<BarnDto> convertToDtoList(List<Barn> barnList)
       {
    	   List<BarnDto> dtoList = new ArrayList<BarnDto>();
    	   for(Barn barn : barnList)
    	   {
    		   dtoList.add(convertToDto(barn));
    	   }
    	   return dtoList;
       }
}
