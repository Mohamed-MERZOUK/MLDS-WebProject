package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControler {

    UserRepository userRepository;

    @Autowired
    public UserControler(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addUser(@RequestBody User user) throws Exception {
        userRepository.save(user);
    }


}
