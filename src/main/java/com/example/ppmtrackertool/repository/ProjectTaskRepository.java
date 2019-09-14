package com.example.ppmtrackertool.repository;

import com.example.ppmtrackertool.domain.Backlog;
import com.example.ppmtrackertool.domain.ProjectTask;
import com.example.ppmtrackertool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    //List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

}
