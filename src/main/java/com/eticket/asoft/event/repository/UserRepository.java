package com.eticket.asoft.event.repository;

import com.eticket.asoft.event.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
}