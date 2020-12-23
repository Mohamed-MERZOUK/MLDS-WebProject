package com.mlds.webProject.restControler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Interest;
import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import com.mlds.webProject.repository.UserRepository;
import com.mlds.webProject.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventControler {

    EventRepository eventRepository;
    UserRepository userRepository;

    @Autowired
    public EventControler(EventRepository eventRepository, UserRepository userRepository) {
        super();
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Event> getEvents(){
        return eventRepository.findAll();
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Event addEvent(@RequestParam("image") MultipartFile multipartFile, @RequestParam("event") String model) throws Exception {

        //get the actual user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user= userRepository.findByUsername(currentPrincipalName);

        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(model, Event.class);

        //add the actual user as owner of the event
        event.setOwner(user);

        //Get the photo original file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        event.setPhoto(fileName);

        //persist the event
        eventRepository.save(event);

        String uploadDir = "user-photos/" + user.getId() + "/" + event.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return event;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Event editEvent(@RequestBody Event event) throws Exception {
        //find the old event by id
        Optional<Event> e = eventRepository.findById((Long)event.getId());
        Event ev = e.get();

        //modify the event
        ev.setTitle(event.getTitle());
        ev.setDate(event.getDate());
        ev.setDescription(event.getDescription());
        ev.setDetail(event.getDetail());

        eventRepository.save(ev);
        return ev;
    }


    @RequestMapping(value = "/participents/{id}", method = RequestMethod.GET)
    public int getNbOfParticipents(@PathVariable("id") Long id) throws Exception {

        //get the event
        Optional<Event> e = eventRepository.findById((Long)id);
        Event ev = e.get();

        //return the participents in the event
        return ev.getParticipents().size();
    }


    @RequestMapping(value = "/interested/{id}", method = RequestMethod.GET)
    public int getNbOfIntrested(@PathVariable("id") Long id) throws Exception {

        //get the event
        Optional<Event> e = eventRepository.findById((Long)id);
        Event ev = e.get();

        //return the interested on the event
        return ev.getIntrested().size();
    }

    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long removeInterest(@RequestBody Event event) throws Exception {
        eventRepository.deleteById(event.getId());
        return event.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @PostMapping("/profile/pic")
    public User addProfilPic(@RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //get the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //get the actual user
        User user= userRepository.findByUsername(currentPrincipalName);

        user.setPhoto(fileName);

        String uploadDir = "user-photos/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        //return the event for the user
        return user;
    }

}
