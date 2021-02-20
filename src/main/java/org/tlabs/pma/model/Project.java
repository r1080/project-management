package org.tlabs.pma.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long projectId;
	@Column
	private String name;
	@Column
	private String stage; // - NOTSTARTED, COMPLETED, INPROGRESS
	@Column
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.DETACH })
	@JoinTable(name = "project_employee", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = {
			@JoinColumn(name = "employee_id") })
	private List<Employee> employeeList;

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project(Long projectId, String name, String stage, String description) {
		this.projectId = projectId;
		this.name = name;
		this.stage = stage;
		this.description = description;
	}

	public Project() {

	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", stage=" + stage + ", description="
				+ description + "]";
	}

}
