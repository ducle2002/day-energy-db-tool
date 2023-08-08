package com.example.demo;

import java.util.List;

import com.example.demo.repository.DayEnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccessingDataMysqlApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	DayEnergyRepository dayEnergyRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMysqlApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}