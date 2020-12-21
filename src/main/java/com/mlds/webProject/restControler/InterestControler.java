package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Interest;
import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import com.mlds.webProject.repository.InterestRepository;
import com.mlds.webProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/interests")
public class InterestControler {

    InterestRepository interestRepository;
    UserRepository userRepository;
    EventRepository eventRepository;

    @Autowired
    public InterestControler(InterestRepository interestRepository,UserRepository userRepository,EventRepository eventRepository) {
        super();
        this.interestRepository = interestRepository;
        this.userRepository=userRepository;
        this.eventRepository=eventRepository;
    }


    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Event addInterest(@RequestBody Event event) throws Exception {

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //get the actual user
        User user = userRepository.findByUsername(currentPrincipalName);


        Optional<Event> e = eventRepository.findById(event.getId());

        Event intrestEvent = e.get();


        //create a new Interest
        Interest intrest = new Interest();


        intrest.setInterested(user);
        intrest.setEvent(intrestEvent);
        user.getIntrests().add(intrest);
        intrestEvent.getIntrested().add(intrest);


        return event;


    }



    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long removeInterest(@RequestBody Event event) throws Exception {

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();


        //get the actual user
        User user = userRepository.findByUsername(currentPrincipalName);

        //get the event
        Optional<Event> e = eventRepository.findById(event.getId());
        Event intrestEvent = e.get();

        //get the interestObject
        Interest interest = interestRepository.findByEventAndInterested(intrestEvent,user);

        //save the interestId
        long interestId = interest.getId();

        //remove the intrest entity from the database
        interestRepository.delete(interest);


        // return the interestId
        return interestId;


    }


}
