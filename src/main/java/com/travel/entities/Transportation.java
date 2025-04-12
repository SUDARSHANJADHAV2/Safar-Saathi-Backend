package com.travel.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transportation")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long transId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private String transportName;

    private Double price;
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

}
