package com.mlds.webProject.repository;

import com.mlds.webProject.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    //List<Event> findByName(String name);
}