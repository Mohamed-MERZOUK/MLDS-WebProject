package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import com.mlds.webProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EventControler {

    EventRepository eventRepository;
    UserRepository userRepository;

    @Autowired
    public EventControler(EventRepository eventRepository, UserRepository userRepository) {
        super();
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/events")
    public Iterable<Event> getEvents(){
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addEvent(@RequestBody Event event) throws Exception {

        //get the actual user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user= userRepository.findByUsername(currentPrincipalName);

        //add the actual user as owner of the event
        event.setOwner(user);

        //persist the event
        eventRepository.save(event);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
    public Event editEvent(@RequestBody Event event, @PathVariable("id") Long id) throws Exception {
        //find the old event by id
        Optional<Event> e = eventRepository.findById((Long)id);
        Event ev = e.get();

        //modify the event
        ev.setTitle(event.getTitle());
        ev.setDate(event.getDate());
        ev.setDescription(event.getDescription());
        ev.setDetail(event.getDetail());

        eventRepository.save(ev);
        return ev;
    }


}
