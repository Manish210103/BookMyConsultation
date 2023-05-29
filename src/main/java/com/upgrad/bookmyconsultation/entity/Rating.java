package com.upgrad.bookmyconsultation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Rating {
    @Id
    private String id = UUID.randomUUID().toString();
    private String appointmentId;
    private Integer rating;
    private String comments;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public String getDoctorId() {
        return id;
    }
}
