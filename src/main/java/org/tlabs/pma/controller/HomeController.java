package org.tlabs.pma.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.model.Employee;
import org.tlabs.pma.model.Project;
import org.tlabs.pma.repository.EmployeeRepository;
import org.tlabs.pma.repository.ProjectRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public String diplayMainDashboard(Model model){
		
		logger.info("Home Main Dashboard");
		
		List<Project> projectsList = new ArrayList<>();
		List<Employee> employeeList = new ArrayList<>();
		
		projectRepository.findAll().forEach(p -> projectsList.add(p));
		model.addAttribute("projectsList", projectsList);
		
		employeeRepository.findAll().forEach(e -> employeeList.add(e));
		model.addAttribute("employeesListProjectsCnt", employeeList);
	
		return "home";
	}	

}
