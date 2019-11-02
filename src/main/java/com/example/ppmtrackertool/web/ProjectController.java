package com.example.ppmtrackertool.web;

import com.example.ppmtrackertool.services.MapValidationErrorService;
import com.example.ppmtrackertool.services.ProjectService;
import com.example.ppmtrackertool.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapvalidationService(bindingResult);
        if (errorMap != null)
            return errorMap;
        Project project1 = projectService.saveOrUpdate(project, principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> findProjectByIdentifier(@PathVariable String projectId, Principal principal) {
        Project project = projectService.getProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){return projectService.findAllProjects(principal.getName());}

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String testMesage() {
        return "Test Message!";
    }
}
