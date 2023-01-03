package com.mako.wawel.persistence.repositories;

import com.mako.wawel.entity.cinema.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
}
