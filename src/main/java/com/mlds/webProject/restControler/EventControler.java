package com.mlds.webProject.restControler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlds.webProject.entity.Event;
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
    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }


    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Event addEvent(@RequestParam("image") MultipartFile multipartFile, @RequestParam("event") String model) throws Exception {

        //get the actual user (connected)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);

        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(model, Event.class);

        //add the actual user as owner of the event
        event.setOwner(user);

        //Get the photo original file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        event.setPhoto(fileName);

        //persist the event
        eventRepository.save(event);

        //save the photo
        String uploadDir = "user-photos/" + user.getId() + "/" + event.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return event;
    }

    //Update Event
    @PutMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Event editEvent(@RequestParam(value = "image", required = false) MultipartFile multipartFile, @RequestParam("event") String model) throws Exception {

        //get the actual user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);

        //get the event
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(model, Event.class);
        Optional<Event> e = eventRepository.findById((Long) event.getId());
        Event ev = e.get();

        //modify the event
        ev.setTitle(event.getTitle());
        ev.setDate(event.getDate());
        ev.setDescription(event.getDescription());
        ev.setDetail(event.getDetail());

        //delete the file from the file system
        if (event.getPhoto() == null) {
            if (ev.getPhoto() == null)
                System.out.println("this event do not have a photo");
            else {
                FileUploadUtil.deleteFile( "user-photos/" + user.getId() + "/" + ev.getId(),ev.getPhoto());
                ev.setPhoto(null);
            }
        }

        //add the new photo
        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            ev.setPhoto(fileName);
            String uploadDir = "user-photos/" + user.getId() + "/" + ev.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        //persist the event
        eventRepository.save(ev);

        return ev;
    }


    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long removeInterest(@RequestBody Event event) throws Exception {
        eventRepository.deleteById(event.getId());
        return event.getId();
    }

}
