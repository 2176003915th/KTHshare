package idusw.leafton.model.service;

import idusw.leafton.model.DTO.EventDTO;

import java.util.List;

public interface EventService {
    public List<EventDTO> getAll();
    EventDTO getEventById(Long eventId);
}