package com.rungroup.runApp.service;

import com.rungroup.runApp.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long id, EventDto eventDto);
    List<EventDto> findAllEvents();
}
