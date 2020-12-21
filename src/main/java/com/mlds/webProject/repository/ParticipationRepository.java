package com.mlds.webProject.repository;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {
    //List<Participation> findByName(String name);
    Participation findByEventAndParticipent(Event event, User participent);
}