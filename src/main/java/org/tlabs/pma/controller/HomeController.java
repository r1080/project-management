package org.tlabs.pma.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.dto.ProjectStage;
import org.tlabs.pma.logging.LogTime;
import org.tlabs.pma.model.Project;
import org.tlabs.pma.service.EmployeeService;
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
	private EmployeeService employeeService;

	@GetMapping("/page/{pageNo}")
	@LogTime
	public String displayMainDashboard(@PathVariable int pageNo, Model model) {

		displayPaginatedProjects(pageNo, model);

		model.addAttribute("employeesListProjectsCnt", employeeService.getEmployeeProjectCount());
		model.addAttribute("versionNumber", version);

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

	@GetMapping("/metrics")
	@LogTime
	public String displayMetricsPage(Model model) {
		
		
		List<Project> completedProjects = projectService.findProjectsByStage("COMPLETED");
		
		model.addAttribute("completedProjects", completedProjects);

		model.addAttribute("timelineData", projectService.getTimelineMetrics());

		return "metrics";
	}

	private void displayPaginatedProjects(int pageNo, Model model) {

		Page<Project> paginatedProjects = projectService.findPaginatedProjects(pageNo, 5);

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("noOfPages", paginatedProjects.getTotalPages());
		model.addAttribute("totalItems", paginatedProjects.getTotalElements());

		List<Project> currentProjects = paginatedProjects.getContent().stream()
				.filter(p -> !p.getStage().equals("COMPLETED")).collect(Collectors.toList());
		
		model.addAttribute("projectsList", currentProjects);

	}

}
