package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public String bookAppointment(Appointment appointment) throws SlotUnavailableException, InvalidInputException {
        ValidationUtils.validate(appointment);
        Appointment existingAppointment = appointmentRepository.findByDoctorIdAndTimeSlotAndAppointmentDate(appointment.getDoctorId(), appointment.getTimeSlot(), appointment.getAppointmentDate());
        if (existingAppointment != null) {
            throw new SlotUnavailableException("Appointment slot is unavailable");
        }
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return savedAppointment.getId();

    }

    public Appointment getAppointment(String appointmentId) throws ResourceUnAvailableException {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceUnAvailableException("Appointment not found"));
    }

    public List<Appointment> getAppointmentsForUser(String userId) {
        return appointmentRepository.findByUserId(userId);
    }
}
