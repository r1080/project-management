package org.tlabs.pma.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.dto.ProjectStage;
import org.tlabs.pma.repository.EmployeeRepository;
import org.tlabs.pma.service.ProjectService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Value("${version}")
	private String version;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public String displayMainDashboard(Model model) {

		model.addAttribute("projectsList", projectService.findAllProjects());
		model.addAttribute("employeesListProjectsCnt", employeeRepository.employeeProjects());
		model.addAttribute("versionNumber",version);

		List<ProjectStage> projectStage = projectService.getProjectStages();

		String projectStageJson = convertToJsonString(projectStage);
		
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
	
	//TODO: metrics controller methods.
	@GetMapping("/metrics")
	public String displayMetricsPage(Model model){
	    
		System.out.println("OUTPUT::: " + projectService.getTimelineMetrics());
		
		model.addAttribute("timelineData", projectService.getTimelineMetrics());
		
		return "metrics";
	}

}
