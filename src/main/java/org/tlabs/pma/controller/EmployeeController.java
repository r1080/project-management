package org.tlabs.pma.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tlabs.pma.model.Employee;
import org.tlabs.pma.repository.EmployeeRepository;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public String displayAddEmployee(Model model){
		
		logger.info("Get --> Employee Add Page");
	
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		
		return "new-employee";
	}
	
	@PostMapping("/save")
	public String addEmployee(Employee employee){
		logger.info("Post --> Employee Add/Save Page");
		employeeRepository.save(employee);
		return "redirect:/employee";
	}
	
	@GetMapping("/view")
	public String getEmployeeList(Model model){
		logger.info("Get --> Employee List");
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(e -> empList.add(e));
		model.addAttribute("employees", empList);
		return "list-employees";
	}

}
