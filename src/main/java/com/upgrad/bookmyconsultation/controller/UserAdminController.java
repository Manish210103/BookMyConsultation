package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.service.AppointmentService;
import com.upgrad.bookmyconsultation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@RequestHeader("authorization") String accessToken,
                                        @PathVariable("id") final String userUuid) {
        final User user = userService.getUser(userUuid);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody final User user) throws InvalidInputException {
        userService.registerUser(user); // Call the service method to register the user
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/{userId}/appointments")
    public ResponseEntity<?> getAppointmentsForUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
    }
}
