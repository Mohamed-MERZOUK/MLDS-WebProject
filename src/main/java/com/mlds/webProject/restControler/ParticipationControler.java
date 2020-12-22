

package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import com.mlds.webProject.repository.ParticipationRepository;
import com.mlds.webProject.repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/participations")
public class ParticipationControler {

    ParticipationRepository participationRepository;
    UserRepository userRepository;
    EventRepository eventRepository;

    @Autowired
    public ParticipationControler(ParticipationRepository participationRepository, UserRepository userRepository, EventRepository eventRepository) {
        super();
        this.participationRepository = participationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Event addParticipations(@RequestBody Event event) throws Exception {

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //get the actual user
        User user = userRepository.findByUsername(currentPrincipalName);

        Optional<Event> e = eventRepository.findById(event.getId());
        Event participationEvent = e.get();

        //get the participationObject
        Participation participation = participationRepository.findByEventAndParticipent(participationEvent,user);

        //don't add it if exists
        if(participation!=null){
             return event;
        }

        //create a new participation
        participation = new Participation();

        participation.setParticipent(user);
        participation.setEvent(participationEvent);
        user.getParticipations().add(participation);
        participationEvent.getParticipents().add(participation);

        return event;
    }



    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long removeParticipations(@RequestBody Event event) throws Exception {

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //get the actual user
        User user = userRepository.findByUsername(currentPrincipalName);

        //get the event
        Optional<Event> e = eventRepository.findById(event.getId());
        Event participationEvent = e.get();

        //get the participationObject
        Participation participation = participationRepository.findByEventAndParticipent(participationEvent,user);


        //remove the participation entity from the database
        participation.dismissEvent();
        participation.dismissParticipent();
        participationRepository.delete(participation);


        // return the participationId
        return e.get().getId();
    }
}
