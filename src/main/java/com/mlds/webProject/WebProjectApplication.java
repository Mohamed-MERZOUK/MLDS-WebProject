package com.mlds.webProject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mlds.webProject.entity.Event;
import com.mlds.webProject.entity.User;
import com.mlds.webProject.repository.EventRepository;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@SpringBootApplication
public class WebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository ur) {
		return (args) -> {
			User u = new User();
			u.setName("I'm the owner");
			u.setType("user");


			Event e = new Event();

//			e.setOwner(u);

			e.setTitle("hello Bank");
			e.setDate(new Date());

			u.getEvents().add(e);
			e.setOwner(u);


			ur.save(u);


			Iterable<User> users = ur.findAll();
			User utemp = users.iterator().next();
			System.out.println(utemp.getName());

			List<Event> events = utemp.getEvents();

			System.out.println(events.size());
		};
	}
}
