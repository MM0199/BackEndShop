package com.tutorial.dreamshops.service.user;

import com.tutorial.dreamshops.dto.OrderDto;
import com.tutorial.dreamshops.dto.UserDto;
import com.tutorial.dreamshops.exception.AlreadyExistsException;
import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.Cart;
import com.tutorial.dreamshops.model.Order;
import com.tutorial.dreamshops.model.Role;
import com.tutorial.dreamshops.model.User;
import com.tutorial.dreamshops.repository.RoleRepository;
import com.tutorial.dreamshops.repository.UserRepository;
import com.tutorial.dreamshops.repository.request.CreateUserRequest;
import com.tutorial.dreamshops.repository.request.UpdateUserRequest;
import com.tutorial.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
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
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map( req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
                    user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
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

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        List<Order> orders = user.getOrders();
        if (orders != null) {
            userDto.setOrders(
                    orders.stream().map(
                            order -> modelMapper.map(order, OrderDto.class)
                    ).collect(toList())
            );
        } else {
            userDto.setOrders(null);
        }
        return userDto;
    }
}
