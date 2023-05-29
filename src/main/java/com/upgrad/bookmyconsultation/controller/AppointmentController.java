package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
        try {
            appointmentService.bookAppointment(appointment);
            return ResponseEntity.ok("Appointment booked successfully");
        } catch (SlotUnavailableException | InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable String appointmentId) {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        return ResponseEntity.ok(appointment);
    }
}
