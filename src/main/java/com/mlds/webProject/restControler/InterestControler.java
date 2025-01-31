package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Interest;
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

import java.util.Date;
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

        //Don't add it if exists
        Interest interest = interestRepository.findByEventAndInterested(intrestEvent,user);
        if(interest!=null){
            return event;
        }

        //Create a new Interest
        Interest intrest = new Interest();

        intrest.setDate(new Date());
        intrest.setInterested(user);
        intrest.setEvent(intrestEvent);
        user.getIntrests().add(intrest);
        intrestEvent.getIntrested().add(intrest);

        //Persist the interest
        interestRepository.save(intrest);

        return event;
    }


    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long removeInterest(@RequestBody Event event) throws Exception {

        //get the actual user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUsername(currentPrincipalName);

        //get the event object
        Optional<Event> e = eventRepository.findById(event.getId());
        Event intrestEvent = e.get();

        //get the interestObject
        Interest interest = interestRepository.findByEventAndInterested(intrestEvent,user);

        //remove the intrest entity from the database
        interest.dismissEvent();
        interest.dismissInterested();
        interestRepository.delete(interest);

        // return the interestId
        return intrestEvent.getId();
    }

}
