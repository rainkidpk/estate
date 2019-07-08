package com.laptrinhjavaweb.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Sorter;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.utils.HttpUtils;

@WebServlet("/api-admin-building")
public class BuildingAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7874518778645326128L;
	private IBuildingService buildingService;

	public BuildingAPI() {
		buildingService = new BuildingService();
		System.out.printf("áđá", buildingService);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		// HttpUtils.of(request.getReader()).toModel(BuildingDTO.class);
		BuildingDTO buildingDTO = HttpUtils.of(request.getReader()).toModel(BuildingDTO.class);
		// buildingDTO = buildingService.save(buildingDTO);
		// buildingDTO = buildingService.update(buildingDTO, buildingDTO.getId());
		 buildingService.delete(buildingDTO, buildingDTO.getId());;
		 //buildingService.findById(id);

		objectMapper.writeValue(response.getOutputStream(), buildingDTO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//long id = Long.parseLong(request.getParameter("id"));
//		BuildingDTO buildingDTO = HttpUtils.of(request.getReader()).toModel(BuildingDTO.class);
		//BuildingDTO buildingDTO = buildingService.findById(id);
		
		Map<String,Object> properties = new HashMap<>();
		properties.put("name", "a");
		properties.put("ward", "a");
		Sorter sorter = new Sorter("name", "desc");
		PageRequest pageRequest = new PageRequest(1, 3, sorter);
		//List<BuildingDTO> buildingDTOs = buildingService.findAll(properties, pageRequest);
		//objectMapper.writeValue(response.getOutputStream(), buildingDTOs);
		
		//objectMapper.writeValue(response.getOutputStream(), buildingDTO);
		
		
	}

//	private Long getId(HttpServletRequest request) {
//		String strId = request.getParameter("id");
//		if(strId != null) {
//			return Long.parseLong(strId);
//		}
//		return null;
//	}

	/*
	 * protected void doPut(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * 
	 * }
	 * 
	 * protected void doDelete(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * 
	 * }
	 */
}
