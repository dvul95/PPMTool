package com.dvulovic.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.Backlog;
import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.domain.ProjectTask;
import com.dvulovic.ppmtool.exceptions.ProjectIdException;
import com.dvulovic.ppmtool.exceptions.ProjectNotFoundException;
import com.dvulovic.ppmtool.repository.BacklogRepository;
import com.dvulovic.ppmtool.repository.ProjectRepository;
import com.dvulovic.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService  projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

		
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			projectTask.setBacklog(backlog);
			Integer BacklogSequence = backlog.getPTSequence();
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getPriority()== null || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}

			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TODO");
			}

			return projectTaskRepository.save(projectTask);	

	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id);

		if (project == null) {
			throw new ProjectNotFoundException("Project with the Id: '" + backlog_id + "' does not exist.");
		}

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findProjectTaskBySequence(String backlog_id, String pt_id) {
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with the Id: '" + backlog_id + "' does not exist.");
		}
		
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '"+ pt_id +"' not found.");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project: '" + backlog_id + "'.");
		}
		
		return projectTask;
	}
	
	//update projectTask 
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		ProjectTask projectTask = findProjectTaskBySequence(backlog_id, pt_id);
		
		projectTask = updatedTask;
		
		return projectTaskRepository.save(projectTask);
	}
	
	//delete task
	
	public void deleteProjectTaskBySequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findProjectTaskBySequence(backlog_id, pt_id);
		
		projectTaskRepository.delete(projectTask);
	}
}
