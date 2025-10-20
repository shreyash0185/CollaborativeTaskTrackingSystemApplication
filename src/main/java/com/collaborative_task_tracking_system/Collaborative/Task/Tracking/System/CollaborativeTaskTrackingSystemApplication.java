package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CollaborativeTaskTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborativeTaskTrackingSystemApplication.class, args);
	}

}



