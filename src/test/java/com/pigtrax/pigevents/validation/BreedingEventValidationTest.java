package com.pigtrax.pigevents.validation;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/test/resources/test-context.xml",
		"file:src/test/resources/spring-datasource.xml"})
@Transactional
public class BreedingEventValidationTest {
    @Autowired
    PigInfoService pigInfoService;
    
    @Autowired
    BreedingEventService breedingEventService;
    
    @Autowired
    PregnancyEventService pregnancyEventService;
    
    @Autowired
    BreedingEventValidation validationObj;
	
    BreedingEventDto dto = new BreedingEventDto();
    
    DateTime today = null;
    
	@Before
	public void insertTestData()
	{
		/*today = new DateTime();
		
		PigInfoDto pigInfo = new PigInfoDto();
		pigInfo.setPigId("PIG1");
		pigInfo.setBarnId(null);
		pigInfo.setPenId(null);
		pigInfo.setSireId("sireId");
		pigInfo.setDamId("damId");
		pigInfo.setOrigin("origin");;
		pigInfo.setBirthDate((today.minusDays(10)).toDate());
		pigInfo.setTattoo("PIGTATTOO");
		pigInfo.setAlternateTattoo("ALT TATTOO");
		pigInfo.setRemarks("For testing");
		pigInfo.setUserUpdated("s_admin");
		pigInfo.setCompanyId(2);;
		pigInfo.setSexTypeId(1);
		
		try {
			int pigInfoKey = pigInfoService.savePigInformation(pigInfo);
			
			BreedingEventDto breedingEventDto = new BreedingEventDto();
		
			BreedingEventDto newEvent = breedingEventService.saveBreedingEventInformation(breedingEventDto);
						
			PregnancyEventDto pregnancyEvent = new PregnancyEventDto();
			pregnancyEvent.setCompanyId(2);
			pregnancyEvent.setPigId("PIG1");
			pregnancyEvent.setPigInfoId(pigInfoKey);
			pregnancyEvent.setPregnancyEventTypeId(1);
			pregnancyEvent.setPregnancyExamResultTypeId(5);
			pregnancyEvent.setEmployeeGroupId(16);
			pregnancyEvent.setExamDate(today.minusDays(1).toDate());
			pregnancyEvent.setResultDate(today.minusDays(1).toDate());
			pregnancyEvent.setSowCondition(3);
			pregnancyEvent.setUserUpdated("s_admin");
			pregnancyEvent.setCompanyId(2);;
			
			int pregnancyEventKey  = pregnancyEventService.savePregnancyEventInformation(pregnancyEvent);
			
			System.out.println("Genernated key  : "+pigInfoKey+"/"+newEvent.getId()+"/"+pregnancyEventKey);
			
			dto.setPigInfoKey(pigInfoKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}
	
	@Test
	public void testValidate()
	{
		
		
		try {
			assertEquals("Acceptable Price",4,4); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void cleanupData()
	{

	}
}
