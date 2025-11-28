package com.grits.habit_tracker;

import org.springframework.boot.SpringApplication;

public class TestHabitTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.from(HabitTrackerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
