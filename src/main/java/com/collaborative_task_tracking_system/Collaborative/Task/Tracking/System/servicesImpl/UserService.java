package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.Exception.ResourceNotFoundException;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.UserDTO;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.UserRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "users", key = "#email") // Cache users by their email for Performance
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return mapToDTO(user);
    }
    public UserDTO updateUser(String email, UserDTO updatedData) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Update only allowed fields
        if (updatedData.getUsername() != null) user.setName(updatedData.getUsername());
        if (updatedData.getPhoneNumber() != null) user.setPhoneNumber(updatedData.getPhoneNumber());
        if (updatedData.getAddress() != null) user.setAddress(updatedData.getAddress());
        if (updatedData.getProfilePictureUrl() != null) user.setProfilePictureUrl(updatedData.getProfilePictureUrl());
        if (updatedData.getStatus() != null) user.setStatus(updatedData.getStatus());

        userRepository.save(user);
        log.info("Updated user: {}", email);

        return mapToDTO(user);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                String.valueOf(user.getId()),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getProfilePictureUrl(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}