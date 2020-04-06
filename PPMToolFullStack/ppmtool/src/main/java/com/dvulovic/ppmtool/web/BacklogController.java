package com.dvulovic.ppmtool.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvulovic.ppmtool.domain.ProjectTask;
import com.dvulovic.ppmtool.services.MapValidationErrorService;
import com.dvulovic.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	//actions
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
		
		ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
		if (mapError != null) return mapError;
		
		ProjectTask pt = projectTaskService.addProjectTask(backlog_id, projectTask);
		
		return new ResponseEntity<ProjectTask>(pt, HttpStatus.CREATED);	
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
		return projectTaskService.findBacklogById(backlog_id);
		
	} 
}
