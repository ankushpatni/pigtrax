package com.pigtrax.pigevents.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PigletEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.util.DateUtil;

@Component
public class PigletEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public PigletEvent convertToBean(PigletEventDto dto)
	   {
		PigletEvent info = new PigletEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getPigletId());
			   info.setTattooId(dto.getTattooId());
			   info.setWeightAtBirth(dto.getWeightAtBirth());
			   info.setWeightAtWeaning(dto.getWeightAtWeaning());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setFarrowEventId(dto.getFarrowEventId());
			   info.setPremiseId(dto.getPremiseId());
			   info.setLitterId(dto.getLitterId());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setWeight1(dto.getWeight1());
			   info.setWeight2(dto.getWeight2());
			   info.setWeight3(dto.getWeight3());
			   info.setWeight4(dto.getWeight4());
			   info.setWeight5(dto.getWeight5());
			   info.setWeight6(dto.getWeight6());
			   info.setWeight7(dto.getWeight7());
			   info.setWeight8(dto.getWeight8());
			   info.setPigId(dto.getPigId());
			   info.setDate1(dto.getDate1());
			   info.setDate2(dto.getDate2());
			   info.setDate3(dto.getDate3());
			   info.setDate4(dto.getDate4());
			   info.setDate5(dto.getDate5());
			   info.setDate6(dto.getDate6());
			   info.setDate7(dto.getDate7());
			   info.setDate8(dto.getDate8());
			   info.setGroupId(dto.getGroupId());
			   
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public PigletEventDto convertToDto(PigletEvent info)
	   {
		   PigletEventDto dto = new PigletEventDto();
		   if(info != null)
		   {
			   dto.setPigletId(info.getId());
			   dto.setTattooId(info.getTattooId());
			   dto.setWeightAtBirth(info.getWeightAtBirth());
			   dto.setWeightAtWeaning(info.getWeightAtWeaning());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setFarrowEventId(info.getFarrowEventId());
			   dto.setPremiseId(info.getPremiseId());
			   dto.setLitterId(info.getLitterId());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setWeight1(info.getWeight1());
			   dto.setWeight2(info.getWeight2());
			   dto.setWeight3(info.getWeight3());
			   dto.setWeight4(info.getWeight4());
			   dto.setWeight5(info.getWeight5());
			   dto.setWeight6(info.getWeight6());
			   dto.setWeight7(info.getWeight7());
			   dto.setWeight8(info.getWeight8());
			   dto.setPigId(info.getPigId());
			   dto.setPigId(info.getPigId());
			   dto.setDate1(info.getDate1());
			   dto.setDate2(info.getDate2());
			   dto.setDate3(info.getDate3());
			   dto.setDate4(info.getDate4());
			   dto.setDate5(info.getDate5());
			   dto.setDate6(info.getDate6());
			   dto.setDate7(info.getDate7());
			   dto.setDate8(info.getDate8());
			   try {
				   dto.setDateStr1(DateUtil.convertToFormatString(dto.getDate1(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   try {
				   dto.setDateStr2(DateUtil.convertToFormatString(dto.getDate2(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   try {
				   dto.setDateStr3(DateUtil.convertToFormatString(dto.getDate3(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   try {
				   dto.setDateStr4(DateUtil.convertToFormatString(dto.getDate4(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   try {
				   dto.setDateStr5(DateUtil.convertToFormatString(dto.getDate5(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   try {
				   dto.setDateStr6(DateUtil.convertToFormatString(dto.getDate6(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setDateStr1(null);
				}
			   dto.setGroupId(info.getGroupId());
			   
			   
		   }
		   return dto;
	   }
	   
	   
	   /** 
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<PigletEventDto> convertToDtos(List<PigletEvent> pigletEvents)
	   {
		   List<PigletEventDto> pigletEventList = null;
		   if(pigletEvents != null)
		   {
			   pigletEventList = new ArrayList<PigletEventDto>();
			   for(PigletEvent event : pigletEvents)
			   {
				   pigletEventList.add(convertToDto(event));
			   }
		   }
		   return pigletEventList;
	   }
}
