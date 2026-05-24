package com.example.it211ss08hw02.aspect;

import com.example.it211ss08hw02.entity.Flight;
import com.example.it211ss08hw02.entity.Ticket;
import com.example.it211ss08hw02.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class BusinessRuleAspect {

    private final TicketRepository ticketRepository;

    @Before("execution(* com.example.it211ss08hw02.service.TicketService.cancelTicket(..)) && args(ticketId)")
    public void checkCancelRule(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));
        Flight flight = ticket.getFlight();

        if (Duration.between(LocalDateTime.now(), flight.getDepartureTime()).toHours() < 24) {
            throw new RuntimeException("Không được hủy vé sát giờ bay (<24h)");
        }
    }

}
