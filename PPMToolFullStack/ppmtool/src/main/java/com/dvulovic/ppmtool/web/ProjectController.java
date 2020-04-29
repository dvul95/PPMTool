package com.dvulovic.ppmtool.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	//CREATE PROJECT
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}

		Project p = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	//GET BY ID
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal) {

		Project project = projectService.findProjectByIdentifier(projectIdentifier, principal.getName());

		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}
	
	//GET ALL
	@GetMapping("/projects")
	public Iterable<Project> getAllProjects(Principal principal){
		return projectService.findAllProjects(principal.getName());
	}
	
	//DELETE 
	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier, Principal principal){
		projectService.deleteProjectByIdentifier(projectIdentifier, principal.getName());
		return new ResponseEntity<String>("Project with Identifier '" +projectIdentifier +"' was successfully deleted .", HttpStatus.OK);
	}

}
