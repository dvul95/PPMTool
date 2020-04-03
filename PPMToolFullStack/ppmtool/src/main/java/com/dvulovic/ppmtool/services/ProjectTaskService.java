package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Backlog;
import com.dvulovic.ppmtool.domain.ProjectTask;
import com.dvulovic.ppmtool.repository.BacklogRepository;
import com.dvulovic.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		
		Integer BacklogSequence = backlog.getPTSequence();

		BacklogSequence++;
		projectTask.setProjectSequence(projectIdentifier+"-"+ BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		
		if(projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}
		
		if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TODO");
		}
		
		return projectTaskRepository.save(projectTask);
		
	}
}
