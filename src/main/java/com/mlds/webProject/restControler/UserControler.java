package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControler {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserControler(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super();
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //@GetMapping
    //public Iterable<User> getUsers(){
    //    return userRepository.findAll();
    //}

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @PostMapping("/sign-up")
    public long signUp(@RequestBody User user) throws Exception {
        user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userRepository.save(user).getId();
    }


//    @Transactional(rollbackFor = Exception.class)
//    public long saveDto(User user) {
//        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
//        return userRepository.save(user).getId();
//    }


}
