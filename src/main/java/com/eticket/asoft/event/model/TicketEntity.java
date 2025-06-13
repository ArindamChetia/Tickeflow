package com.eticket.asoft.event.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JsonIgnore
    private EventEntity event;

  

    // Getters and Setters
    // ...
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}
    
}
