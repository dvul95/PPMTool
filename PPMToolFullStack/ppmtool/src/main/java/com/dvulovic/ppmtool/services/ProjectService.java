package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Backlog;
import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.exceptions.ProjectIdException;
import com.dvulovic.ppmtool.repository.BacklogRepository;
import com.dvulovic.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if (project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase()+ "' already exists.");
		}			
	}
	//get the Project by Identifier
	public Project findProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project Id '" + projectIdentifier + "' does not exist.");
		}
		return project;
	}
	//Get all Projects
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectIdentifier) {
		
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Cannot delete project with the Identifier '" + projectIdentifier +"'. The project does not exist.");
		}
		
		projectRepository.delete(project);
	}
}
