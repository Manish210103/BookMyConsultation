package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Address;
import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.enums.Speciality;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.repository.AddressRepository;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.DoctorRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import com.upgrad.bookmyconsultation.model.TimeSlot;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AddressRepository addressRepository;

    public Doctor register(Doctor doctor) throws InvalidInputException {
        try {
            ValidationUtils.validateDoctor(doctor);

            if (doctor.getAddress() == null) {
                throw new InvalidInputException("Address is required");
            }

            doctor.setId(UUID.randomUUID().toString());

            if (doctor.getSpeciality() == null) {
                doctor.setSpeciality(Speciality.GENERAL_PHYSICIAN);
            }

            Address address = new Address();
            address.setAddressLine1(doctor.getAddress().getAddressLine1());
            address.setAddressLine2(doctor.getAddress().getAddressLine2());
            address.setCity(doctor.getAddress().getCity());
            address.setState(doctor.getAddress().getState());
            address.setPostcode(doctor.getAddress().getPostcode());

            Address savedAddress = addressRepository.save(address);

            doctor.setAddress(savedAddress);

            Doctor savedDoctor = doctorRepository.save(doctor);

            return savedDoctor;
        } catch (Exception e) {
            log.error("Error occurred while registering doctor", e);
            throw new InvalidInputException("Invalid input provided");
        }
    }

    public Doctor getDoctor(String id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            return optionalDoctor.get();
        } else {
            throw new ResourceUnAvailableException("Doctor not found with id: " + id);
        }
    }

    public List<Doctor> getAllDoctorsWithFilters(String speciality) {
        if (speciality != null && !speciality.isEmpty()) {
            return doctorRepository.findBySpecialityOrderByRatingDesc(Speciality.valueOf(speciality));
        }
        return getActiveDoctorsSortedByRating();
    }

    @Cacheable(value = "doctorListByRating")
    private List<Doctor> getActiveDoctorsSortedByRating() {
        log.info("Fetching doctor list from the database");
        return doctorRepository.findAllByOrderByRatingDesc()
                .stream()
                .limit(20)
                .collect(Collectors.toList());
    }

    public TimeSlot getTimeSlots(String doctorId, String date) throws InvalidInputException {
        try {
            TimeSlot timeSlot = new TimeSlot(doctorId, date);
            timeSlot.setTimeSlot(timeSlot.getTimeSlot()
                    .stream()
                    .filter(slot -> appointmentRepository
                            .findByDoctorIdAndTimeSlotAndAppointmentDate(timeSlot.getDoctorId(), slot, timeSlot.getAvailableDate()) == null)
                    .collect(Collectors.toList()));
            return timeSlot;
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input provided");
        }
    }
}
