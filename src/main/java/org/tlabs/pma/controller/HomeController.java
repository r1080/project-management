package org.tlabs.pma.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.dto.ProjectStage;
import org.tlabs.pma.repository.EmployeeRepository;
import org.tlabs.pma.repository.ProjectRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public String diplayMainDashboard(Model model) {

		logger.info("Home Main Dashboard");
		model.addAttribute("projectsList", projectRepository.findAll());
		model.addAttribute("employeesListProjectsCnt", employeeRepository.employeeProjects());

		List<ProjectStage> projectStage = projectRepository.projectStage();

		String projectStageJson = convertToJsonString(projectStage);
		System.out.println("Json String ::  " + projectStageJson);
		
		model.addAttribute("projectStatusCnt", projectStageJson);

		return "home";
	}

	private String convertToJsonString(List<ProjectStage> projectStage) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(projectStage);
		} catch (JsonProcessingException e) {
			logger.error("Error converting to json {}", e);
		}
		return jsonString;
	}

}
