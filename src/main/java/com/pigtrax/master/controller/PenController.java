package com.pigtrax.master.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.RoomService;

@Controller
public class PenController {

	private static final Logger logger = Logger.getLogger(PenController.class);

	@Autowired
	RoomService roomService;
	
	@Autowired
	RefDataCache refDataCache;
	
	@RequestMapping(value = "/pen", method=RequestMethod.POST)
	public String displayPen(Model model, @RequestParam int generatedRoomId)
	{
		System.out.println("Ankush printing---->"+generatedRoomId);
		model.addAttribute("contentUrl","pen.jsp");
		model.addAttribute("generatedBarnId",generatedRoomId);
		try
		{
			Room room = roomService.findByRoomByAutoGeneratedId(generatedRoomId);
			if(null != room)
			{
				model.addAttribute("roomId",room.getRoomId());				
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "template";
	}
	
	@RequestMapping(value = "/addPen")
	public String addPen(Model model)
	{
		model.addAttribute("contentUrl","addPen.jsp"); 
		return "addPen";
	}
}
