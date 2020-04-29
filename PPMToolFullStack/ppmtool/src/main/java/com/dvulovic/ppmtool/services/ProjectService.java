package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Backlog;
import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.domain.User;
import com.dvulovic.ppmtool.exceptions.ProjectIdException;
import com.dvulovic.ppmtool.exceptions.ProjectNotFoundException;
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
	
	//Create / Update 
	public Project saveOrUpdateProject(Project project, String username) {
		
		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in this account");
			}else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' can't be updated. It doesn't exist.");
			}
		}
		
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
	public Project findProjectByIdentifier(String projectIdentifier, String username) {	
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		
		if (project == null) {
			throw new ProjectIdException("Project Id '" + projectIdentifier + "' does not exist.");
		}
		
		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in this account!");
		}
		return project;
	}
	
	//Get all Projects
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	//Delete the project
	public void deleteProjectByIdentifier(String projectIdentifier, String username) {
		
		projectRepository.delete(findProjectByIdentifier(projectIdentifier, username));
	}
}
