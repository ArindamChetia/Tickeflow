package com.eticket.asoft.event.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eticket.asoft.event.model.EventEntity;
import com.eticket.asoft.event.model.TicketEntity;
import com.eticket.asoft.event.model.UserEntity;
import com.eticket.asoft.event.repository.EventRepository;
import com.eticket.asoft.event.repository.TicketRepository;
import com.eticket.asoft.event.services.EmailService;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private EmailService e;
    

//    public void cancelTicket(Long ticketId) {
//        TicketEntity ticket = ticketRepository.findById(ticketId)
//                .orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//        EventEntity event = ticket.getEvent();
//        event.setCapacity(event.getCapacity() + 1); // Restore capacity
//        eventRepository.save(event);
//
//        ticketRepository.delete(ticket);
//    }
//    
    public boolean cancelTicket(Long ticketId) {
        Optional<TicketEntity> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }

        TicketEntity ticket = ticketOpt.get();
        EventEntity event = ticket.getEvent();
        UserEntity user = ticket.getUser();

        // Increase capacity
        event.setCapacity(event.getCapacity() + 1);
        eventRepository.save(event);

        // Delete ticket
        ticketRepository.delete(ticket);

        // Send cancellation email
        e.sendCancellationEmail(user.getEmail().toString(), event.getTitle().toString());

        return true;
    }
}