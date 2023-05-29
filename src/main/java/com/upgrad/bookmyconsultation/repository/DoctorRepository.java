package com.upgrad.bookmyconsultation.repository;

import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.enums.Speciality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, String> {

	List<Doctor> findBySpecialityOrderByRatingDesc(Speciality speciality);

	List<Doctor> findAllByOrderByRatingDesc();

	Optional<Doctor> findById(UUID doctorId);
}
