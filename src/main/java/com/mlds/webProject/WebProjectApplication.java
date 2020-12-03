package com.mlds.webProject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.Interest;
import com.mlds.webProject.entity.Participation;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
import com.mlds.webProject.repository.InterestRepository;
import com.mlds.webProject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@SpringBootApplication
public class WebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}

	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

//	@Bean
//	public CommandLineRunner demo(UserRepository ur, InterestRepository ir) {
//		return (args) -> {
//			User u = new User();
//			u.setName("I'm the owner 2");
//			u.setType("admin");
//
//
//			Event e = new Event();
//
////			e.setOwner(u);
//
//			e.setTitle("hello Bank 2");
//			e.setDate(new Date());
//
//			u.getEvents().add(e);
//			e.setOwner(u);
//
//
//
//			Optional<User> u1 = ur.findById((long) 1);
//
//			Participation p = new Participation();
//			p.setParticipent(u1.get());
//			p.setEvent(e);
////			u.getParticipations().add(p);
////			e.getParticipents().add(p);
//
//			ur.save(u);
//
//
//
//			Interest i = new Interest();
//			i.setInterested(u1.get());
//			i.setEvent(e);
//			ir.save(i);
////			u.getIntrests().add(i);
////			e.getIntrested().add(i);
//
//
//
//
////			Iterable<User> users = ur.findAll();
////			User utemp = users.iterator().next();
////			System.out.println(utemp.getName());
//
////			List<Interest> events = utemp.getIntrests();
//
////			System.out.println(events.size());
//		};
//	}
}
