package com.travel.entities;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "packages")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "trips")
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    private String packageName;
    private Double price;

    @Column(length = 2000)
    private String description;

    @Column(name = "image_url", length = 2000)
    private String imageUrl;

    @Column(length = 2000)
    private String highlights;

    private String restaurants;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private User vendor;

    @OneToMany(mappedBy = "selectedPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Trip> trips = new ArrayList<>();
}