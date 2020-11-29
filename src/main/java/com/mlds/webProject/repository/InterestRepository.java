package com.mlds.webProject.repository;

import com.mlds.webProject.entity.Interest;
import org.springframework.data.repository.CrudRepository;

public interface InterestRepository extends CrudRepository<Interest, Long> {
    //List<Participation> findByName(String name);
}