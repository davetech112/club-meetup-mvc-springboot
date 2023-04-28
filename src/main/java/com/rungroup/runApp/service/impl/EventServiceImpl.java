package com.rungroup.runApp.service.impl;

import com.rungroup.runApp.dto.EventDto;
import com.rungroup.runApp.models.Club;
import com.rungroup.runApp.models.Event;
import com.rungroup.runApp.repository.ClubRepository;
import com.rungroup.runApp.repository.EventRepository;
import com.rungroup.runApp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.runApp.mapper.EventMapper.mapToEvent;
import static com.rungroup.runApp.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long id, EventDto eventDto) {
        Club club = clubRepository.findById(id).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }


}
