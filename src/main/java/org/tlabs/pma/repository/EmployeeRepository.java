package org.tlabs.pma.repository;

import org.springframework.data.repository.CrudRepository;
import org.tlabs.pma.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
