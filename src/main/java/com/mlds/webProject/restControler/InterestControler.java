package com.mlds.webProject.restControler;

import com.mlds.webProject.entity.Interest;
import com.mlds.webProject.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestControler {

    InterestRepository interestRepository;

    @Autowired
    public InterestControler(InterestRepository interestRepository) {
        super();
        this.interestRepository = interestRepository;
    }

    @GetMapping("/interests")
    public Iterable<Interest> getInterests(){
        return interestRepository.findAll();
    }

    @PostMapping("/interests")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addInterest(@RequestBody Interest interest) throws Exception {
        interestRepository.save(interest);
    }


}
