package com.eticket.asoft.event.controller;

import com.eticket.asoft.event.model.*;
import com.eticket.asoft.event.repository.*;
import com.eticket.asoft.event.services.EmailService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("/api")
public class MainController {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final TicketRepository ticketRepo;
    private final EmailService emailService;

    public MainController(EventRepository eventRepo, UserRepository userRepo, TicketRepository ticketRepo, EmailService emailService) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
        this.emailService = emailService;
    }

    // Event APIs
    @GetMapping("/events")
    public List<EventEntity> getAllEvents() {
        return eventRepo.findAll();
    }

    @GetMapping("/events/search")
    public List<EventEntity> searchEvents(@RequestParam(required = false) String title,
                                    @RequestParam(required = false) String location) {
        if (title != null && !title.isEmpty()) {
            return eventRepo.findByTitleContainingIgnoreCase(title);
        } else if (location != null && !location.isEmpty()) {
            return eventRepo.findByLocationContainingIgnoreCase(location);
        } else {
            return eventRepo.findAll();
        }
    }

    @PostMapping("/events")
    public EventEntity createEvent(@RequestBody EventEntity event) {
        return eventRepo.save(event);
    }

    // User APIs
    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepo.save(user);
    }
    
 // Get users who booked tickets for a specific event
    @GetMapping("/events/{eventId}/users")
    public ResponseEntity<?> getUsersForEvent(@PathVariable Long eventId) {
        List<TicketEntity> tickets = ticketRepo.findByEventId(eventId);
        List<UserEntity> users = tickets.stream()
                .map(TicketEntity::getUser)
                .distinct()
                .toList();
        return ResponseEntity.ok(users);
    }

    // Ticket Booking
    @PostMapping("/tickets")
    public ResponseEntity<String> bookTicket(@RequestParam Long userId, @RequestParam Long eventId) {
        Optional<UserEntity> userOpt = userRepo.findById(userId);
        Optional<EventEntity> eventOpt = eventRepo.findById(eventId);

        if (userOpt.isPresent() && eventOpt.isPresent()) {
            EventEntity event = eventOpt.get();
            if (event.getTickets().size() >= event.getCapacity()) {
                return ResponseEntity.badRequest().body("Event is full");
            }

            TicketEntity ticket = new TicketEntity();
            ticket.setUser(userOpt.get());
            ticket.setEvent(event);
            ticketRepo.save(ticket);

            emailService.sendBookingConfirmation(userOpt.get().getEmail(), event.getTitle(),ticket.getId());

            return ResponseEntity.ok("Ticket booked successfully and its id is:" + ticket.getId());
        } else {
            return ResponseEntity.badRequest().body("Invalid user or event ID");
        }
    }
    
    @GetMapping("/tickets/all")
    public List<TicketEntity> getAllTicketBookings() {
        return ticketRepo.findAll();
    }
    //Download the ticket
    
    @GetMapping("/tickets/{ticketId}/pdf")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable Long ticketId) {
        Optional<TicketEntity> ticketOpt = ticketRepo.findById(ticketId);

        if (ticketOpt.isPresent()) {
            TicketEntity ticket = ticketOpt.get();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, out);
                document.open();
                document.add(new Paragraph("\uD83C\uDFAB Event Ticket"));
                document.add(new Paragraph("Event: " + ticket.getEvent().getTitle()));
                document.add(new Paragraph("Attendee: " + ticket.getUser().getName()));
                document.add(new Paragraph("Email: " + ticket.getUser().getEmail()));
                document.add(new Paragraph("Location: " + ticket.getEvent().getLocation()));
                document.add(new Paragraph("Date: " + ticket.getEvent().getEventDate().toString()));
                document.close();

                byte[] pdfBytes = out.toByteArray();

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes);

            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}