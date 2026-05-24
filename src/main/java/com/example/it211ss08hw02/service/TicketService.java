package com.example.it211ss08hw02.service;

import com.example.it211ss08hw02.entity.Flight;
import com.example.it211ss08hw02.entity.Ticket;
import com.example.it211ss08hw02.repository.FlightRepository;
import com.example.it211ss08hw02.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket bookTicket(String flightNumber, String passengerName) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null) {
            throw new RuntimeException("Không tìm thấy chuyến bay");
        }
        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("Hết vé");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        Ticket ticket = new Ticket();
        ticket.setPassengerName(passengerName);
        ticket.setFlight(flight);
        ticket.setStatus("BOOKED");

        return ticketRepository.save(ticket);
    }


    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));
        ticket.setStatus("CANCELED");
        ticketRepository.save(ticket);
    }
}

