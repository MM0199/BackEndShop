package com.tutorial.dreamshops.service.user;

import com.tutorial.dreamshops.dto.UserDto;
import com.tutorial.dreamshops.model.User;
import com.tutorial.dreamshops.request.CreateUserRequest;
import com.tutorial.dreamshops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest user);

    User updateUser(UpdateUserRequest user, Long userId);

    void deleteUser(Long userId);

    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
