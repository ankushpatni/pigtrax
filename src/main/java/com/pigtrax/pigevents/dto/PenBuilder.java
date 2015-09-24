package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.Pen;

@Component
public class PenBuilder {
	
	/**
	 * Covert Barn object to Dto
	 * @param Pen
	 * @return
	 */
       public PenDto convertToDto(Pen pen)
       {
    	   if(pen != null)
    	   {
    		   PenDto dto = new PenDto();
    		   dto.setId(pen.getId());
    		   dto.setPenId(pen.getPenId());
    		   dto.setActive(pen.isActive());
    		   dto.setLocation(pen.getLocation());
    		   dto.setRoomId(pen.getRoomId());
    		   dto.setLastUpdated(pen.getLastUpdated());
    		   dto.setUserUpdated(pen.getUserUpdated());
    		   return dto;
    	   }
    	   return null;
       }
       
       
       /**
        * Convert List of Pens to Dtos
        * @param penList
        * @return
        */
       public List<PenDto> convertToDtoList(List<Pen> penList)
       {
    	   List<PenDto> dtoList = new ArrayList<PenDto>();
    	   for(Pen pen : penList)
    	   {
    		   dtoList.add(convertToDto(pen));
    	   }
    	   return dtoList;
       }
}
