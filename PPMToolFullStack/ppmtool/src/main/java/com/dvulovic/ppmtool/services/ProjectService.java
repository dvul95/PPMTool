package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Backlog;
import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.domain.User;
import com.dvulovic.ppmtool.exceptions.ProjectIdException;
import com.dvulovic.ppmtool.repository.BacklogRepository;
import com.dvulovic.ppmtool.repository.ProjectRepository;
import com.dvulovic.ppmtool.repository.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	BacklogRepository backlogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project, String username) {
		
		try {
			
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
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
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	//Delete the project
	public void deleteProjectByIdentifier(String projectIdentifier) {
		
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Cannot delete project with the Identifier '" + projectIdentifier +"'. The project does not exist.");
		}
		
		projectRepository.delete(project);
	}
}
