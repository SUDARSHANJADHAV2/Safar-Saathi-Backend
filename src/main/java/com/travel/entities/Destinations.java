package com.travel.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "destination")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Destinations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;

    private String destinationName;

    @Column(name = "image_url")
    private String imageUrl;
}