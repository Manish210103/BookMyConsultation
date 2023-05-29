package com.upgrad.bookmyconsultation.repository;

import com.upgrad.bookmyconsultation.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String> {

    List<Appointment> findByUserId(String userId);

    Appointment findByDoctorIdAndTimeSlotAndAppointmentDate(String doctorId, String timeSlot, String appointmentDate);

    default boolean isPresent(String appointmentId) {
        Optional<Appointment> optionalAppointment = findById(appointmentId);
        return optionalAppointment.isPresent();
    }
}
