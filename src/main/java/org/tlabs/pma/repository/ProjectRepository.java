package org.tlabs.pma.repository;

import org.springframework.data.repository.CrudRepository;
import org.tlabs.pma.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
