package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @PostMapping("/sign-up")
    public long signUp(@RequestBody User user) throws Exception {
        user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userRepository.save(user).getId();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @GetMapping("/profile")
    public User profile() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
//        System.out.println(currentPrincipalName);
        User user= userRepository.findByUsername(currentPrincipalName);
        User temp = new User(user);


        return temp;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @PostMapping("/profile")
    public User profile(@RequestBody User user) throws Exception {

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //get the actual user
        User usertmp= userRepository.findByUsername(currentPrincipalName);



        //only for the attribut password
        if(user.getPassword()!=null){
            usertmp.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
        }

        //update the user
        usertmp.setUser(user);
        User temp = new User();

        //clone the user and remove the password
        temp.setUser(usertmp);
        temp.setPassword(null);
        return temp;
    }




}
