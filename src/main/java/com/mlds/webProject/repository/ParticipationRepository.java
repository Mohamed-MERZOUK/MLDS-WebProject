package com.mlds.webProject.repository;

import com.mlds.webProject.entity.Participation;
import org.springframework.data.repository.CrudRepository;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {
    //List<Participation> findByName(String name);
}