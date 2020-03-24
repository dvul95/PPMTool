package com.dvulovic.ppmtool.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvulovic.ppmtool.domain.Project;
import com.dvulovic.ppmtool.services.MapValidationErrorService;
import com.dvulovic.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}

		Project p = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {

		Project project = projectService.findProjectByIdentifier(projectIdentifier);

		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}
	
	@GetMapping("/projects")
	public Iterable<Project> getAllProjects(){
		return projectService.findAllProjects();
	}

}
