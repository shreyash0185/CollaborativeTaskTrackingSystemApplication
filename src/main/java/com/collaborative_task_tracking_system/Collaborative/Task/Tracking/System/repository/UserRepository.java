package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    

    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);
}
