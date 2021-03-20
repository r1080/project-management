package org.tlabs.pma.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/new")
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

		System.out.println("project id " + project.getProjectId());
		projectRepository.save(project);

		return "redirect:/project/view";
	}
	
	@GetMapping("/view")
	public String displayProjects(Model model){
		
		model.addAttribute("projects", projectRepository.findAll());
		
		return "list-projects";
	}
	
	@GetMapping("/update")
	public String updateProject(Model model,@RequestParam("id") Long projectId){
		Optional<Project> optional = projectRepository.findById(projectId);
		if(optional.isPresent()){
			System.out.println("Id from DB " + optional.get());
			model.addAttribute("project", optional.get());
			
			List<Employee> employeeList = new ArrayList<>();
			employeeRepository.findAll().forEach(e -> employeeList.add(e));
			
			model.addAttribute("allEmployees", employeeList);
			return "new-project";
		}
		return "list-projects";		
	}
	
	@GetMapping("/delete")
	public String deleteProject(Model model, @RequestParam("id") Long projectId){
		projectRepository.deleteById(projectId);
		return "redirect:/project/view";
	}
}
