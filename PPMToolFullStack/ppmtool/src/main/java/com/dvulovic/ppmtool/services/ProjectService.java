package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.exceptions.ProjectIdException;
import com.dvulovic.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase()+ "' already exists.");
		}			
	}
	
	public Project findProjectByIdentifier(String projectIdentifier) {
		return projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
	}
}
