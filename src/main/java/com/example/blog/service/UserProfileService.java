package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.entity.UserProfile;
import com.example.blog.repository.UserProfileRepository;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    // Получить все профили
    public List<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }

    // Получить профиль по ID
    public Optional<UserProfile> getProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    // Получить профиль по userId
    public Optional<UserProfile> getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }

    // Проверить, есть ли у пользователя профиль
    public boolean userHasProfile(Long userId) {
        return userProfileRepository.findByUserId(userId).isPresent();
    }

    // Создать новый профиль
    @Transactional
    public UserProfile createProfile(Long userId, UserProfile userProfile) {
        if (userHasProfile(userId)) {
            throw new RuntimeException("Profile already exists for this user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userProfile.setUser(user); // Связываем профиль с пользователем
        return userProfileRepository.save(userProfile);
    }



    // Обновить профиль по ID
    public Optional<UserProfile> updateProfile(Long id, UserProfile updatedProfile) {
        return userProfileRepository.findById(id).map(existingProfile -> {
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setLastName(updatedProfile.getLastName());
            existingProfile.setBio(updatedProfile.getBio());
            existingProfile.setAvatarUrl(updatedProfile.getAvatarUrl());
            return userProfileRepository.save(existingProfile);
        });
    }

    // Удалить профиль по ID
    public boolean deleteProfile(Long id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
