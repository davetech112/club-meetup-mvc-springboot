package com.rungroup.runApp.repository;

import com.rungroup.runApp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long > {
}
