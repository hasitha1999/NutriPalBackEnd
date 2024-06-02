package com.example.NutriPal;

import ch.qos.logback.core.encoder.Encoder;
import com.example.NutriPal.entity.LogType;
import com.example.NutriPal.entity.Role;
import com.example.NutriPal.entity.User;
import com.example.NutriPal.repository.LogTypeRepository;
import com.example.NutriPal.repository.RoleRepository;
import com.example.NutriPal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class NutriPalApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private LogTypeRepository logTypeRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(NutriPalApplication.class, args);
	}
	@Override
	public void run(final String... args) throws Exception {
		if(userRepository.count() == 0) {
			Role roleAdmin = new Role(null, "ADMIN");
			Role roleUser = new Role(null, "USER");

			User userAdmin = User.builder()
					.userName("testAdmin")
					.firstName("Test")
					.lastName("Admin")
					.email("testadmin@gmail.com")
					.password(passwordEncoder.encode("1234"))
					.weight(80.0)
					.isDeleted(false)
					.isActive(true)
					.role(roleAdmin)
					.build();

			User user = User.builder()
					.userName("testUser")
					.firstName("Test")
					.lastName("User")
					.email("hasithalakmal0617@gmail.com")
					.password(passwordEncoder.encode("1234"))
					.weight(80.0)
					.isDeleted(false)
					.isActive(true)
					.role(roleUser)
					.build();

			roleRepository.save(roleAdmin);
			roleRepository.save(roleUser);
			userRepository.save(userAdmin);
			userRepository.save(user);

		}
		if (logTypeRepository.count() ==0){

			ArrayList<LogType> logTypesList = new ArrayList<>();

			LogType logTypeWater = LogType.builder()
					.type("Water")
					.build();

			LogType logTypeCalorie = LogType.builder()
					.type("Calorie")
					.build();

			LogType logTypeWeight = LogType.builder()
					.type("Weight")
					.build();

			logTypesList.add(logTypeWater);
			logTypesList.add(logTypeCalorie);
			logTypesList.add(logTypeWeight);

			logTypeRepository.saveAllAndFlush(logTypesList);
		}
	}

}
