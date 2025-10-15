package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
    
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Finds a user by their email address.
     * 
     * @param email the email to search for
     * @return the user with the given email, or null if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email the email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
