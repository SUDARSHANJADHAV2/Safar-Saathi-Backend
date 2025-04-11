package com.travel.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accommodation")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accomId;

	@ManyToOne
	@JoinColumn(name = "trip_id")
	@JsonIgnore
	private Trip trip;

	private String hotelName;
	private String address;
	private Double price;
	private LocalDateTime checkIn;
}