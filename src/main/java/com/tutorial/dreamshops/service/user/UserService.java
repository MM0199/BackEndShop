package com.tutorial.dreamshops.service.user;

import com.tutorial.dreamshops.exception.AlreadyExistsException;
import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.User;
import com.tutorial.dreamshops.repository.UserRepository;
import com.tutorial.dreamshops.repository.request.CreateUserRequest;
import com.tutorial.dreamshops.repository.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public User getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional
                .of(request)
                .filter(user ->
                        !userRepository.existsByEmail(request.getEmail()))
                .map( req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("User already exists!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
           existingUser.setFirstName(request.getFirstName());
           existingUser.setLastName(request.getLastName());
           return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository
                .findById(userId)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> { throw new ResourceNotFoundException("User not found!"); }
                );
    }
}
