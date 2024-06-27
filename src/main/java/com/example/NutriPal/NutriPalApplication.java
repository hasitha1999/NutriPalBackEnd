package com.example.NutriPal;

import ch.qos.logback.core.encoder.Encoder;
import com.example.NutriPal.entity.*;
import com.example.NutriPal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	private AllergyRepository allergyRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DietTypeRepository dietTypeRepository;

	public static void main(String[] args) {
		SpringApplication.run(NutriPalApplication.class, args);
	}
	@Override
	public void run(final String... args) throws Exception {
		if(userRepository.count() == 0) {
			Role roleAdmin = new Role(null, "ADMIN");
			Role roleUser = new Role(null, "USER");



			User userAdmin = User.builder()
					.gymID("admin202406")
					.firstName("Test")
					.lastName("Admin")
					.email("testadmin@gmail.com")
					.password(passwordEncoder.encode("1234"))
					.weight(80.0)
					.isDeleted(false)
					.isActive(true)
					.role(roleAdmin)
					.build();
			Allergy allergygluten = new Allergy("gluen");
			Allergy allergyseafood = new Allergy("seafood");
			Allergy allergybean = new Allergy("bean");

			Set<Allergy> allergySet = new HashSet<>();
			allergySet.add(allergygluten);
			allergySet.add(allergyseafood);
			allergySet.add(allergybean);

			User user = User.builder()
					.gymID("user202406")
					.firstName("Test")
					.lastName("User")
					.email("hasithalakmal0617@gmail.com")
					.password(passwordEncoder.encode("1234"))
					.weight(80.0)
					.allergy(allergySet)
					.isDeleted(false)
					.isActive(true)
					.role(roleUser)
					.build();

			allergyRepository.saveAll(allergySet);
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

			LogType logTypeFat = LogType.builder()
					.type("Fat")
					.build();

			LogType logTypeProtein = LogType.builder()
					.type("Protein")
					.build();

			LogType logTypeCarbohydrate = LogType.builder()
					.type("Carbohydrate")
					.build();

			logTypesList.add(logTypeWater);
			logTypesList.add(logTypeCalorie);
			logTypesList.add(logTypeWeight);
			logTypesList.add(logTypeFat);
			logTypesList.add(logTypeProtein);
			logTypesList.add(logTypeCarbohydrate);

			logTypeRepository.saveAllAndFlush(logTypesList);


		}
		if(dietTypeRepository.count() == 0){
			ArrayList<DietType> dietTypesList = new ArrayList<>();

			DietType dietType1 = DietType.builder().dietName("balanced").build();
			DietType dietType2 = DietType.builder().dietName("high-protein").build();
			DietType dietType3 = DietType.builder().dietName("low-carb").build();
			DietType dietType4 = DietType.builder().dietName("low-fat").build();

			dietTypesList.add(dietType1);
			dietTypesList.add(dietType2);
			dietTypesList.add(dietType3);
			dietTypesList.add(dietType4);

			dietTypeRepository.saveAllAndFlush(dietTypesList);

		}
	}

}
