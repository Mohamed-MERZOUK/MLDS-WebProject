package com.mlds.webProject.repository;

import com.mlds.webProject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    //List<User> findByName(String name);
}