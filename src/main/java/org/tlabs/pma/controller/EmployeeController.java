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
import org.tlabs.pma.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public String displayAddEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new-employee";
	}

	@PostMapping("/save")
	public String addEmployee(Employee employee) {
		employeeRepository.save(employee);
		return "redirect:/employee";
	}

	@GetMapping("/view")
	public String getEmployeeList(Model model) {
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(e -> empList.add(e));
		model.addAttribute("employees", empList);
		return "list-employees";
	}

	@GetMapping("/update")
	public String updateEmployee(Model model, @RequestParam("id") Long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		if (optional.isPresent()) {
			model.addAttribute(model.addAttribute("employee", optional.get()));
			return "new-employee";
		}
		return "list-employees";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(Model model, @RequestParam("id") Long id) {
		employeeRepository.deleteById(id);
		return "redirect:/employee/view";
	}

}