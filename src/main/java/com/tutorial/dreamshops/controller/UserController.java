package com.tutorial.dreamshops.controller;

import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.User;
import com.tutorial.dreamshops.repository.request.CreateUserRequest;
import com.tutorial.dreamshops.repository.request.UpdateUserRequest;
import com.tutorial.dreamshops.response.ApiResponse;
import com.tutorial.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.version}/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/user-id")
    public ResponseEntity<ApiResponse> getUser(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", userService.getUserById(userId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", userService.createUser(request)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success!", userService.updateUser(request, userId)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
