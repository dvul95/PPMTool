package com.dvulovic.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dvulovic.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
	// get the Identifier
	Project findByProjectIdentifier(String projectIdentifier);

	//get all projects
	@Override
	Iterable<Project> findAll();
	
	Iterable<Project> findAllByProjectLeader(String username);
	
	
	
}
