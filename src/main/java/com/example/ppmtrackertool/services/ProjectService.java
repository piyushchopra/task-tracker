package com.example.ppmtrackertool.services;

import com.example.ppmtrackertool.domain.Backlog;
import com.example.ppmtrackertool.domain.Project;
import com.example.ppmtrackertool.domain.User;
import com.example.ppmtrackertool.exception.ProjectIdException;
import com.example.ppmtrackertool.exception.ProjectNotFoundException;
import com.example.ppmtrackertool.repository.BacklogRepository;
import com.example.ppmtrackertool.repository.ProjectRepository;
import com.example.ppmtrackertool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdate(Project project, String userName) {
        try {

            User user = userRepository.findByUsername(userName);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        } catch (Exception e) {
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project getProjectByIdentifier(String projectid, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Project Id: "+projectid+" doesn't exist");
        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public Project findProjectByIdentifier(String projectId, String username){
        //Only want to return the project if the user looking for it is the owner
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '"+projectId+"' does not exist");

        }
        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public void deleteProjectByIdentifier(String projectid, String username){
        projectRepository.delete(findProjectByIdentifier(projectid, username));
    }
}
