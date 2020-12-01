package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventControler {

    EventRepository eventRepository;

    @Autowired
    public EventControler(EventRepository eventRepository) {
        super();
        this.eventRepository = eventRepository;
    }

    @GetMapping("/events")
    public Iterable<Event> getEvents(){
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addEvent(@RequestBody Event event) throws Exception {
        eventRepository.save(event);
    }


}
