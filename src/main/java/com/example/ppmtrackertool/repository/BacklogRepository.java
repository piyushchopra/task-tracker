package com.example.ppmtrackertool.repository;

import com.example.ppmtrackertool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String Identifier);
}
