package org.tlabs.pma.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.model.Employee;
import org.tlabs.pma.model.Project;
import org.tlabs.pma.repository.EmployeeRepository;
import org.tlabs.pma.repository.ProjectRepository;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/view")
	public String displayProjectPage(Model model) {

		List<Employee> employeeList = new ArrayList<>();
		employeeRepository.findAll().forEach(e -> employeeList.add(e));

		Project project = new Project();
		model.addAttribute("project", project);
		model.addAttribute("allEmployees", employeeList);

		return "new-project";
	}

	@PostMapping("/save")
	public String saveProject(Model model, Project project) {

		projectRepository.save(project);

		return "redirect:/project/view";
	}

}
