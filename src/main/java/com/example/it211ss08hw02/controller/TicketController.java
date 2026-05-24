package com.example.it211ss08hw02.controller;

import com.example.it211ss08hw02.entity.Ticket;
import com.example.it211ss08hw02.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket request) {
        if (request.getPassengerName() == null || request.getPassengerName().trim().isEmpty()) {
            throw new RuntimeException("Tên hành khách không được rỗng");
        }
        Ticket ticket = ticketService.bookTicket(request.getFlight().getFlightNumber(), request.getPassengerName());
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.ok("Đã hủy vé thành công");
    }
}
