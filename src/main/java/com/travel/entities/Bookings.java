package com.travel.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@OneToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;

	private LocalDate bookingDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "bookings_status", length = 50)
	private BookingStatus bookingsStatus;
}