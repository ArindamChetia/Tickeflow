package com.eticket.asoft.event.repository;

import com.eticket.asoft.event.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
	List<TicketEntity> findByEventId(Long eventId);
}