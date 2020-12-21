package com.mlds.webProject.repository;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Interest;
import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface InterestRepository extends CrudRepository<Interest, Long> {

    Interest findByEventAndInterested(Event event, User Interested);
}