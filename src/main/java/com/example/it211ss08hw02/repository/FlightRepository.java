package com.example.it211ss08hw02.repository;

import com.example.it211ss08hw02.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findByFlightNumber(String flightNumber);
}