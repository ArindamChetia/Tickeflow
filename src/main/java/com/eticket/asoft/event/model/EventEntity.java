package com.eticket.asoft.event.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
	private String description;
    private String location;
    private LocalDateTime eventDate;
    private int capacity;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TicketEntity> tickets = new HashSet<>();

    // Getters and Setters
    // ...
    public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}

  	public String getTitle() {
  		return title;
  	}

  	public void setTitle(String title) {
  		this.title = title;
  	}

  	public String getDescription() {
  		return description;
  	}

  	public void setDescription(String description) {
  		this.description = description;
  	}

  	public String getLocation() {
  		return location;
  	}

  	public void setLocation(String location) {
  		this.location = location;
  	}

  	public LocalDateTime getEventDate() {
  		return eventDate;
  	}

  	public void setEventDate(LocalDateTime eventDate) {
  		this.eventDate = eventDate;
  	}

  	public int getCapacity() {
  		return capacity;
  	}

  	public void setCapacity(int capacity) {
  		this.capacity = capacity;
  	}

  	public Set<TicketEntity> getTickets() {
  		return tickets;
  	}

  	public void setTickets(Set<TicketEntity> tickets) {
  		this.tickets = tickets;
  	}
}